/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.communicate.webSocket;

import com.footprintcat.frostiot.topology.communicate.CommunicationTool;
import com.footprintcat.frostiot.topology.communicate.CommunicationType;
import com.footprintcat.frostiot.topology.pojo.ConnectInfo;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.java_websocket.framing.CloseFrame.NORMAL;

public class WebSocketCommunicationTool implements CommunicationTool {
    private ConnectInfo config;
    private WebSocketServer server;
    private WebSocketClient client;
    private boolean isServerRunning = false;
    private boolean isClientConnected = false;

    // 重连相关
    private int currentReconnectDelay = 1; // 当前重连间隔时间
    private ScheduledFuture<?> heartbeatFuture;
    private ScheduledFuture<?> reconnectFuture;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private String targetServerUri; // 存储目标URI，用于重连

    // 存储所有连接到此服务端的客户端会话
    private final Set<WebSocket> serverConnections = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public WebSocketCommunicationTool(ConnectInfo config) {
        this.config = config;
    }

    @Override
    public void init(ConnectInfo config) {
        System.out.println("WebSocket初始化...");
        startServer();
    }

    /**
     * 启动 WebSocket 服务器
     */
    private void startServer() {
        if (isServerRunning) {
            System.out.println("[" + getType() + "] 服务器已在运行。");
            return;
        }

        this.server = new WebSocketServer(new java.net.InetSocketAddress(config.getPort())) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
                serverConnections.add(conn);
                System.out.println("[" + config.getLocalId() + "] 新连接: " + conn.getRemoteSocketAddress());
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean b) {
                serverConnections.remove(conn);
                System.out.println("[" + config.getLocalId() + "] 连接关闭: " + conn.getRemoteSocketAddress() + ", 原因: " + reason);
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                // 服务端处理心跳
                if ("ping".equals(message)) {
                    conn.send("pong");
                    return;
                }
                String source = conn.getRemoteSocketAddress().getAddress().getHostAddress() + ":" + conn.getRemoteSocketAddress().getPort();
                System.out.println("[" + config.getLocalId() + "] 收到来自 '" + source + "' 的消息: " + message);
                onReceive(message, source);
            }

            @Override
            public void onError(WebSocket webSocket, Exception e) {
                System.err.println("[" + config.getLocalId() + "] 发生错误: ");
                e.printStackTrace();
            }

            @Override
            public void onStart() {
                System.out.println("[" + config.getLocalId() + "] WebSocket 服务器已启动！");
                isServerRunning = true;
                // 启动以后的回调

            }
        };
        server.start();
    }

    public void connectToServer(String target) {
        this.targetServerUri = target;
        createAndConnectClient();
    }

    private void createAndConnectClient() {
        if (isClientConnected) {
            System.out.println("[" + getType() + "] 客户端已连接。");
            return;
        }
        try {
            URI serverUri = new URI(targetServerUri);
            this.client = new WebSocketClient(serverUri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("[" + config.getLocalId() + "] 已连接到服务器: " + targetServerUri);
                    isClientConnected = true;
                    currentReconnectDelay = 1;
                    // 取消可能正在进行的重连任务
                    if (reconnectFuture != null) {
                        reconnectFuture.cancel(false);
                    }
                    // 启动心跳
                    startHeartbeat();
                }

                @Override
                public void onMessage(String message) {
                    String source = getURI().toString();
                    System.out.println("[" + config.getLocalId() + "] 收到来自 '" + source + "' 的消息: " + message);
                    onReceive(message, source);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("[" + config.getLocalId() + "] 与服务器的连接已关闭：" + reason);
                    isClientConnected = false;
                    // 停止心跳
                    stopHeartbeat();
                    // 如果不是主动关闭，则启动重连
                    if (code != NORMAL) {
                        scheduleReconnect();
                    }
                }

                @Override
                public void onError(Exception ex) {
                    System.out.println("[" + config.getLocalId() + "] 客户端发生错误：");
                    ex.printStackTrace();
                    // 发生错误也触发重连
                    isClientConnected = false;
                    stopHeartbeat();
                    scheduleReconnect();
                }
            };
            client.connect();
        } catch (URISyntaxException e) {
            System.err.println("[" + getType() + "] 无效的 URI: " + targetServerUri);
        }
    }

    private void startHeartbeat() {
        stopHeartbeat(); // 确保没有重复的心跳任务
        heartbeatFuture = scheduler.scheduleAtFixedRate(() -> {
            if (isClientConnected && client != null && client.isOpen()) {
                client.send("ping");
            }
        }, 10, 30, TimeUnit.SECONDS); // 10秒后开始，每30秒发送一次心跳
    }

    private void stopHeartbeat() {
        if (heartbeatFuture != null) {
            heartbeatFuture.cancel(false);
            heartbeatFuture = null;
        }
    }

    private void scheduleReconnect() {
        if (reconnectFuture != null && !reconnectFuture.isDone()) {
            return; // 已有重连任务在执行
        }

        System.out.println("[" + config.getLocalId() + "] 将在 " + currentReconnectDelay + " 秒后尝试重连...");
        reconnectFuture = scheduler.schedule(() -> {
            System.out.println("[" + config.getLocalId() + "] 正在尝试重连...");
            createAndConnectClient();
            // 指数退避
            currentReconnectDelay = Math.min(currentReconnectDelay * 2, 30);
        }, currentReconnectDelay, TimeUnit.SECONDS);
    }

    @Override
    public void sendMessage(String message, String target, String replyToUrl) {
        // 客户端发送消息
        if (isClientConnected && client != null && client.isOpen()) {
            client.send(message);
            System.out.println("[" + config.getLocalId() + "] sendMessage: 已向服务器发送消息: " + message);
            return;
        }

        // 服务端发送消息
        if (isServerRunning && !serverConnections.isEmpty()) {
            System.out.println("[" + config.getLocalId() + "] 向所有客户端广播消息：" + message);
            serverConnections.forEach(conn -> {
                if (conn.isOpen()) {
                    conn.send(message);
                }
            });
        } else {
            System.out.println("[" + getType() + "] 无可用连接，无法发送消息。请先作为客户端启动服务器");
        }
    }

    @Override
    public void shutdown() {
        if (client != null && client.isOpen()) {
            client.close();
        }

        if (server != null) {
            try {
                server.stop();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        isServerRunning = false;
        isClientConnected = false;
        onConnectClose(null);
    }

    @Override
    public CommunicationType getType() {
        return CommunicationType.WEBSOCKET;
    }

    @Override
    public boolean isConnected() {
        return isServerRunning || isClientConnected;
    }
}
