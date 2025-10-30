/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.communicate.webSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprintcat.frostiot.common.dto.master.ClientInfoDTO;
import com.footprintcat.frostiot.common.enums.MessageTypeEnum;
import com.footprintcat.frostiot.common.enums.NodeTypeEnum;
import com.footprintcat.frostiot.common.internal.ICurrentNodeInfo;
import com.footprintcat.frostiot.topology.communicate.CommunicationTool;
import com.footprintcat.frostiot.common.enums.CommunicationTypeEnum;
import com.footprintcat.frostiot.topology.pojo.message.Message;
import com.footprintcat.frostiot.topology.pojo.topo.TopologyNode;
import lombok.extern.slf4j.Slf4j;
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
import java.util.concurrent.atomic.AtomicBoolean;

import static org.java_websocket.framing.CloseFrame.NORMAL;

@Slf4j
public class WebSocketCommunicationTool implements CommunicationTool {

    private ClientInfoDTO config;
    private WebSocketServer server;
    private WebSocketClient client;
    private final AtomicBoolean isServerRunning = new AtomicBoolean(false);
    private final AtomicBoolean isClientConnected = new AtomicBoolean(false);

    // 重连相关
    private int currentReconnectDelay = 1; // 当前重连间隔时间
    private ScheduledFuture<?> heartbeatFuture;
    private ScheduledFuture<?> reconnectFuture;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private String targetServerUri; // 存储目标URI，用于重连

    // 存储所有连接到此服务端的客户端会话
    private final Set<WebSocket> serverConnections = Collections.newSetFromMap(new ConcurrentHashMap<>());

    // 序列化工具类
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final ICurrentNodeInfo currentNodeInfo;
    private final TopologyNode topologyNode;

    public WebSocketCommunicationTool(ClientInfoDTO config, ICurrentNodeInfo currentNodeInfo, TopologyNode topologyNode) {
        this.config = config;
        this.currentNodeInfo = currentNodeInfo;
        log.info("currentNodeInfo: hashCode={}", currentNodeInfo.hashCode());
        this.topologyNode = topologyNode;
    }

    @Override
    public void init(ClientInfoDTO config) {
        System.out.println("WebSocket初始化...");
        startServer();
    }

    /**
     * 启动 WebSocket 服务器
     */
    private void startServer() {
        if (isServerRunning.get()) {
            System.out.println("[" + getType() + "] 服务器已在运行。");
            return;
        }

        this.server = new WebSocketServer(new java.net.InetSocketAddress(config.getPort())) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
                serverConnections.add(conn);
                System.out.println("[" + config.getProtocol() + "] 新连接: " + conn.getRemoteSocketAddress());
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean b) {
                serverConnections.remove(conn);
                System.out.println("[" + config.getProtocol() + "] 连接关闭: " + conn.getRemoteSocketAddress() + ", 原因: " + reason);
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                // 服务端处理心跳
                if ("ping".equals(message)) {
                    conn.send("pong");
                    return;
                }
                String source = conn.getRemoteSocketAddress().getAddress().getHostAddress() + ":" + conn.getRemoteSocketAddress().getPort();
                System.out.println("[" + config.getProtocol() + "] 收到来自 '" + source + "' 的消息: " + message);

                try {
                    // 反序列化 JSON 为 Message 对象 ---
                    Message receivedMessage = objectMapper.readValue(message, Message.class);
                    System.out.println("[" + config.getProtocol() + "] 收到消息 (ID: " + receivedMessage.getId() + ", 类型: " + receivedMessage.getType().getCode() + ")");

                    // 根据消息类型分发处理
                    dispatchMessage(receivedMessage, conn);

                } catch (JsonProcessingException e) {
                    System.err.println("[" + config.getProtocol() + "] 无法解析 JSON 消息: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("[" + config.getProtocol() + "] 处理消息时发生未知错误: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(WebSocket webSocket, Exception e) {
                System.err.println("[" + config.getProtocol() + "] 发生错误: ");
                e.printStackTrace();
            }

            @Override
            public void onStart() {
                System.out.println("[" + config.getProtocol() + "] 服务器已启动！");
                isServerRunning.set(true);
                // 启动以后的回调
                onConnected();
            }
        };
        server.start();
    }

    /**
     * 根据消息类型分发到不同的处理方法
     *
     * @param message 接收到的消息对象
     * @param conn    发送消息的客户端连接
     */
    private void dispatchMessage(Message message, WebSocket conn) {
        // 将发送方节点ID添加到访问路径中
        message.getVisitedNodeIds().add(message.getCurrentNodeId());

        switch (message.getType()) {
            case HEARTBEAT:
                handleHeartbeat(message, conn);
                break;
            case CONNECTION_EVENT:
                handleConnectionEvent(message, conn);
                break;
            case DEVICE_MESSAGE:
                handleDeviceMessage(message, conn);
                break;
            default:
                System.err.println("[" + config.getProtocol() + "] 收到未知类型的消息: " + message.getType());
                break;
        }
    }

    private void handleHeartbeat(Message message, WebSocket conn) {
        System.out.println("[" + config.getProtocol() + "] 处理心跳消息，来自节点: " + message.getCurrentNodeId());
    }

    private void handleConnectionEvent(Message message, WebSocket conn) {
        System.out.println("[" + config.getProtocol() + "] 处理连接事件: " + message.getPayload());
        // 在这里可以解析 payload 中的 nodeId 和 nodeType
        // 对方的 nodeId 和 nodeType
        // 当前的连接方式
        // 上下级
    }

    private void handleDeviceMessage(Message message, WebSocket conn) {
        System.out.println("[" + config.getProtocol() + "] 处理设备报文: " + message.getPayload());
        // 调用具体的业务逻辑来处理设备数据
        onReceive(message.getPayload(), message.getCurrentNodeId());
    }

    public void connectToServer(String target) {
        this.targetServerUri = target;
        createAndConnectClient();
    }

    private void createAndConnectClient() {
        if (isClientConnected.get()) {
            System.out.println("[" + getType() + "] 客户端已连接。");
            return;
        }
        try {
            URI serverUri = new URI(targetServerUri);
            this.client = new WebSocketClient(serverUri) {
                @Override
                public void onOpen(ServerHandshake handshakeData) {
                    System.out.println("[" + config.getProtocol() + "] 已连接到服务器: " + targetServerUri);
                    isClientConnected.set(true);
                    currentReconnectDelay = 1;
                    // 取消可能正在进行的重连任务
                    if (reconnectFuture != null) {
                        reconnectFuture.cancel(false);
                    }

                    try {
                        // 建立连接以后，将节点信息发送给服务端
                        String nodeId = currentNodeInfo.getNodeId();
                        String nodeTypeStr = currentNodeInfo.getNodeType();
                        NodeTypeEnum typeEnum = NodeTypeEnum.getByCode(nodeTypeStr);
                        if (typeEnum == null || nodeId == null) {
                            throw new RuntimeException("节点Id/类型有误！");
                        }
                        String nodeType = typeEnum.getCode();

                        // 构建 JSON 格式的注册消息
                        String registerPayload = String.format("{\"nodeId\": \"%s\", \"nodeType\": \"%s\"}", nodeId, nodeType);
                        Message registerMessage = new Message(registerPayload, MessageTypeEnum.CONNECTION_EVENT);
                        // 将 Message 对象序列化为 JSON 字符串
                        String jsonMessage = objectMapper.writeValueAsString(registerMessage);
                        System.out.println("[" + config.getProtocol() + "] 发送注册信息: " + jsonMessage);
                        this.send(jsonMessage);

                    } catch (Exception e) {
                        System.err.println("[" + config.getProtocol() + "] 获取或发送注册信息时出错: " + e.getMessage());
                    }
                    // 启动心跳
                    startHeartbeat();
                }

                @Override
                public void onMessage(String message) {
                    if ("pong".equals(message)) {
                        return;
                    }
                    String source = getURI().toString();
                    System.out.println("[" + config.getProtocol() + "] 收到来自 '" + source + "' 的消息: " + message);
                    onReceive(message, source);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("[" + config.getProtocol() + "] 与服务器的连接已关闭：" + reason);
                    isClientConnected.set(false);
                    // 停止心跳
                    stopHeartbeat();
                    // 如果不是主动关闭，则启动重连
                    if (code != NORMAL) {
                        scheduleReconnect();
                    }
                }

                @Override
                public void onError(Exception ex) {
                    System.out.println("[" + config.getProtocol() + "] 客户端发生错误：");
                    ex.printStackTrace();
                    // 发生错误也触发重连
                    isClientConnected.set(false);
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
            if (isClientConnected.get() && client != null && client.isOpen()) {
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

        System.out.println("[" + config.getProtocol() + "] 将在 " + currentReconnectDelay + " 秒后尝试重连...");
        reconnectFuture = scheduler.schedule(() -> {
            System.out.println("[" + config.getProtocol() + "] 正在尝试重连...");
            createAndConnectClient();
            // 指数退避
            currentReconnectDelay = Math.min(currentReconnectDelay * 2, 30);
        }, currentReconnectDelay, TimeUnit.SECONDS);
    }

    @Override
    public void sendMessage(Message messageEntity, String target, String replyToUrl) {
        try {
            // 在发送前，记录当前节点ID
            String currenNodeId = currentNodeInfo.getNodeId();
            messageEntity.setCurrentNodeId(currenNodeId);
            // 将 Message 对象序列化为 JSON 字符串
            String jsonMessage = objectMapper.writeValueAsString(messageEntity);

            // 客户端发送
            if (isClientConnected.get() && client != null && client.isOpen()) {
                client.send(jsonMessage);
                System.out.println("[" + config.getProtocol() + "] sendMessage: 已向服务器发送消息 (ID: " + messageEntity.getId() + ")");
                return;
            }

            // 服务端广播
            if (isServerRunning.get() && !serverConnections.isEmpty()) {
                System.out.println("[" + config.getProtocol() + "] broadcast: 向所有客户端广播消息 (ID: " + messageEntity.getId() + ")");
                serverConnections.forEach(conn -> {
                    if (conn.isOpen()) {
                        conn.send(jsonMessage);
                    }
                });
                return;
            }
            System.err.println("[" + getType() + "] 无可用连接，无法发送消息。");

        } catch (JsonProcessingException e) {
            System.err.println("[" + getType() + "] 序列化消息失败 (ID: " + messageEntity.getId() + "): " + e.getMessage());
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

        isServerRunning.set(false);
        isClientConnected.set(false);
        onConnectClose(null);
    }

    @Override
    public CommunicationTypeEnum getType() {
        return CommunicationTypeEnum.WEBSOCKET;
    }

    @Override
    public boolean isConnected() {
        return isServerRunning.get() || isClientConnected.get();
    }
}
