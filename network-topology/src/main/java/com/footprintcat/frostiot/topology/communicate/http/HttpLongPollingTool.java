/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.communicate.http;

import com.footprintcat.frostiot.topology.communicate.CommunicationTool;
import com.footprintcat.frostiot.topology.communicate.CommunicationType;
import com.footprintcat.frostiot.topology.pojo.ConnectInfo;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.Executors;

public class HttpLongPollingTool implements CommunicationTool {
    private HttpServer server;
    private HttpClient client;
    private ConnectInfo config;
    private boolean connected = false;

    @Override
    public void init(ConnectInfo config) {
        if (isConnected()) {
            System.out.println("[" + getType() + "] 已经初始化，无需重复操作。");
            return;
        }
        if (config.getType() != CommunicationType.HTTP) {
            throw new IllegalArgumentException("HttpLongPollingTool 不支持 " + config.getType() + " 类型。");
        }

        this.config = config;
        // 配置客户端，设置连接超时
        this.client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

        try {
            server = HttpServer.create(new InetSocketAddress(config.getPort()), 0);
            server.createContext("/long-poll", new LongPollingHandler());
            server.setExecutor(Executors.newCachedThreadPool()); // 使用缓存线程池处理并发请求
            server.start();
            this.connected = true;
            onConnect();
        } catch (IOException e) {
            System.err.println("[" + getType() + "] 初始化失败: " + e.getMessage());
            onConnectClose(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(String message, String target, String replyToUrl) {
        System.out.println("[" + getType() + "] 警告: 对于长轮询，请使用 sendAndWaitForReply 方法。");
    }

    /**
     * 发送一个长轮询请求，并同步等待响应。
     *
     * @param message        要发送的消息
     * @param target         目标URL
     * @param timeoutSeconds 超时时间（秒）
     * @return 服务器的响应，如果超时或出错则返回null
     */
    public String sendAndWaitForReply(String message, String target, int timeoutSeconds) {
        if (!isConnected()) {
            System.err.println("未连接，无法发送消息。");
            return null;
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(target))
                .header("Content-Type", "text/plain; charset=UTF-8")
                .timeout(Duration.ofSeconds(timeoutSeconds)) // 设置总超时时间
                .POST(HttpRequest.BodyPublishers.ofString(message, StandardCharsets.UTF_8))
                .build();

            System.out.println("[" + config.getLocalId() + "] 发送长轮询请求，等待 " + timeoutSeconds + " 秒...");
            // send 方法是阻塞的，会一直等到响应或超时
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("[" + config.getLocalId() + "] 收到响应: " + response.body());
                return response.body();
            } else {
                System.err.println("[" + config.getLocalId() + "] 服务器返回错误: " + response.statusCode());
                return null;
            }
        } catch (HttpTimeoutException e) {
            System.err.println("[" + config.getLocalId() + "] 请求超时！");
            return null;
        } catch (Exception e) {
            System.err.println("[" + config.getLocalId() + "] 发送消息失败: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void shutdown() {
        if (server != null) {
            server.stop(1);
            this.connected = false;
            onConnectClose(null);
        }
    }

    @Override
    public CommunicationType getType() {
        return CommunicationType.HTTP;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    private class LongPollingHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // 读取请求
                InputStream inputStream = exchange.getRequestBody();
                String message = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                String source = exchange.getRemoteAddress().getAddress().getHostAddress();
                onReceive(message, source);

                // 异步处理，原地阻塞
                // 将“何时响应”的逻辑交给后台线程，避免阻塞服务器的主接收线程
                Executors.newSingleThreadExecutor().submit(() -> {
                    try {
                        // 模拟一个耗时操作
                        System.out.println("[" + config.getLocalId() + "] 收到请求，开始处理...");
                        Thread.sleep(5000); // 模拟耗时5秒

                        // 处理完毕，通过原连接返回响应
                        String reply = "这是对 '" + message + "' 的延迟回复。";
                        byte[] responseBytes = reply.getBytes(StandardCharsets.UTF_8);
                        exchange.sendResponseHeaders(200, responseBytes.length);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(responseBytes);
                        }
                        System.out.println("[" + config.getLocalId() + "] 已发送回复。");
                    } catch (Exception e) {
                        // 如果处理出错，也要返回一个错误响应，否则客户端会一直等
                        try {
                            String errorReply = "服务器处理错误: " + e.getMessage();
                            byte[] responseBytes = errorReply.getBytes(StandardCharsets.UTF_8);
                            exchange.sendResponseHeaders(500, responseBytes.length);
                            try (OutputStream os = exchange.getResponseBody()) {
                                os.write(responseBytes);
                            }
                        } catch (IOException ioException) {
                            System.err.println("发送错误响应失败: " + ioException.getMessage());
                        }
                        System.err.println("处理请求时发生错误: " + e.getMessage());
                    }
                });
            } else {
                // 处理非POST请求
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
}
