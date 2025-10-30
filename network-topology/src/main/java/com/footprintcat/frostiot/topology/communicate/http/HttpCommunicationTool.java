/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.communicate.http;

import com.footprintcat.frostiot.common.dto.master.ConnectionInfoDTO;
import com.footprintcat.frostiot.topology.communicate.CommunicationTool;
import com.footprintcat.frostiot.common.enums.CommunicationTypeEnum;
import com.footprintcat.frostiot.topology.pojo.message.Message;
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
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class HttpCommunicationTool implements CommunicationTool {
    private HttpServer server;
    private HttpClient client;
    private ConnectionInfoDTO config;
    private boolean connected = false;

    @Override
    public void init(ConnectionInfoDTO config) {
        if (!config.getProtocol().equals(CommunicationTypeEnum.HTTP.getCode())) {
            throw new IllegalArgumentException("HttpCommunicationTool 不支持 " + config.getProtocol() + " 类型的连接。");
        }

        this.config = config;
        this.client = HttpClient.newHttpClient();

        int port = config.getPort();

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/message", new MessageHandler());
            server.setExecutor(Executors.newSingleThreadExecutor());
            server.start();
            this.connected = true;
            onConnected();
        } catch (IOException e) {
            System.err.println("[" + getType() + "] 初始化失败: " + e.getMessage());
            onConnectClose(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(Message message, String target, String replyToUrl) {
        if (!isConnected()) {
            System.err.println("[" + getType() + "] 未连接，无法发送消息。");
            return;
        }

        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(target))
                .header("Content-Type", "text/plain; charset=UTF-8");

            if (replyToUrl != null && !replyToUrl.isEmpty()) {
                requestBuilder.header("X-Reply-To", replyToUrl);
            }

            HttpRequest request = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(message.getPayload(), StandardCharsets.UTF_8))
                .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("[" + getType() + "] sendMessage: 已向 '" + target + "' 发送消息: " + message);

        } catch (Exception e) {
            System.err.println("[" + getType() + "] 发送消息失败: " + e.getMessage());
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
    public CommunicationTypeEnum getType() {
        return CommunicationTypeEnum.HTTP;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    private class MessageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream inputStream = exchange.getRequestBody();
                String message = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                String source = exchange.getRemoteAddress().getAddress().getHostAddress() + ":" + exchange.getRemoteAddress().getPort();
                onReceive(message, source);

                // 检查是否需要回信
                String replyToUrl = exchange.getRequestHeaders().getFirst("X-Reply-To");
                if (replyToUrl != null && !replyToUrl.isEmpty()) {
                    System.out.println("[" + getType() + "] 准备向 '" + replyToUrl + "' 回信...");
                    // 使用同一个 client 实例发送回信
                    sendReply("这是来自 '" + config.getId() + "' 连接的回复！", replyToUrl);
                }
            }

            String response = "Message received";
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    /**
     * http 回信请求
     *
     * @param replyMessage
     * @param targetUrl
     */
    private void sendReply(String replyMessage, String targetUrl) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Content-Type", "text/plain; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(replyMessage, StandardCharsets.UTF_8))
                .build();

            // 异步发送回信
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> System.out.println("[" + getType() + "] 回信成功。"))
                .exceptionally(e -> {
                    System.err.println("[" + getType() + "] 回信失败: " + e.getMessage());
                    return null;
                });
        } catch (Exception e) {
            System.err.println("[" + getType() + "] 创建回信请求失败: " + e.getMessage());
        }
    }
}
