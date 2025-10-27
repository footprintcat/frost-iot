/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo.topo;

import com.footprintcat.frostiot.topology.communicate.CommunicationType;
import com.footprintcat.frostiot.topology.communicate.http.HttpLongPollingTool;
import com.footprintcat.frostiot.topology.pojo.ConnectInfo;
import com.footprintcat.frostiot.topology.pojo.message.Message;

import java.util.*;
import java.util.concurrent.*;


/**
 * 节点服务
 */
public class NodeService {
    private final HttpLongPollingTool server = new HttpLongPollingTool(); // HTTP服务端

    private final String nodeId;
    private final NodeConfig config;
    private final TopoNetwork network;
    private final NodeStateManager stateManager;
    private final ScheduledExecutorService scheduler;
    private final Map<String, String> connectedNodes; // 已连接的节点信息

    private volatile boolean running = false;

    public NodeService(TopoNetwork network, String resourcePath) throws Exception {
        this.config = NodeConfigLoader.loadFromResources(resourcePath);
        this.nodeId = config.getNode().getNodeId();
        this.network = network;
        this.stateManager = new NodeStateManager(network);
        this.scheduler = Executors.newScheduledThreadPool(3);
        this.connectedNodes = new ConcurrentHashMap<>();

        // 初始化节点
        initializeNode();
    }

    /**
     * 启动节点服务
     */
    public void start() throws Exception {
        if (running) {
            return;
        }

        running = true;

        // 1. 启动状态管理器
        stateManager.start();

        // 2. 启动HTTP服务端
        startHttpServer();

        // // 3. 注册到服务发现
        // registerToDiscovery();
        //
        // // 4. 启动心跳任务
        startHeartbeatTask();
        //
        // // 5. 启动节点发现任务
        // startNodeDiscoveryTask();

        // 6. 标记节点上线
        stateManager.nodeOnline(nodeId, config.getNode().getHost(), config.getNode().getPort());

        connectNodes();

        System.out.println("节点服务启动成功: " + nodeId + " [" +
            config.getNode().getHost() + ":" + config.getNode().getPort() + "]");
    }

    /**
     * 停止节点服务
     */
    public void stop() {
        if (!running) {
            return;
        }

        running = false;

        // 1. 标记节点下线
        stateManager.nodeOffline(nodeId, "服务停止");

        // 2. 从服务发现注销
        // unregisterFromDiscovery();

        // 3. 停止HTTP服务
        server.shutdown();

        // 4. 停止状态管理器
        stateManager.stop();

        // 5. 停止调度器
        scheduler.shutdown();

        System.out.println("节点服务已停止: " + nodeId);
    }

    /**
     * 初始化节点
     */
    private void initializeNode() {
        System.out.println("--- 初始化节点 ---");
        // 创建当前节点
        TopoNode currentNode = createTopoNodeFromConfig(config);
        network.addNode(currentNode);

        // 添加监听器
        stateManager.addStateListener(new NodeStateListener() {
            @Override
            public void onNodeStateChanged(TopoNode node, TopoNode.TopoNodeStatus newStatus, String reason) {
                handleNodeStateChange(node, newStatus, reason);
            }

            @Override
            public void onNetworkStabilityChanged(double stability, String message) {
                System.out.println("网络稳定性变化: " + message);
            }
        });
    }

    /**
     * 启动HTTP服务端
     */
    private void startHttpServer() {
        // server = new Server(config.getNode().getServicePort());
        ConnectInfo connectInfo = ConnectInfo.builder()
            .type(CommunicationType.HTTP)
            .localId(config.getNode().getNodeId())
            .host("localhost")
            .port(config.getNode().getPort())
            .build();

        server.init(connectInfo);

        System.out.println("长轮询服务端 '" + config.getNode().getNodeId() + "' 正在运行，监听端口 " + config.getNode().getPort() + "...");

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    /**
     * 发送消息
     */
    public void sendMessage(Message message, String serverLongPollUrl) {
        String reply = server.sendAndWaitForReply(message, serverLongPollUrl, 15);
        if (reply != null) {
            System.out.println("客户端成功收到回信: " + reply);
        } else {
            System.out.println("客户端未收到回信或发生超时/错误。");
        }
    }

    /**
     * 启动心跳任务
     */
    private void startHeartbeatTask() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!running) return;

            try {
                // 发送心跳
                int currentConnections = getCurrentConnections();

                stateManager.processHeartbeat(nodeId, currentConnections);

                // 向已连接的节点发送心跳
                for (String connectedNodeId : connectedNodes.keySet()) {
                    sendHeartbeatToNode(connectedNodeId);
                }

            } catch (Exception e) {
                System.err.println("心跳任务执行失败: " + e.getMessage());
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }

    /**
     * 发现并连接其他节点
     */
    private void connectNodes() {
        // 1. 从服务发现获取其他节点信息
        List<String> upstreamNodes = config.getTopology().getUpstreamNodes();
        List<String> downstreamNodes = config.getTopology().getDownstreamNodes();

        // 2. 根据拓扑配置连接上下游节点
        for (String upstreamNodeId : upstreamNodes) {
            TopoNode node = network.getNode(upstreamNodeId);
            connectToNode(node);
        }

        for (String downstreamNodeId : downstreamNodes) {
            TopoNode node = network.getNode(downstreamNodeId);
            connectToNode(node);
        }
    }

    /**
     * 连接到其他节点
     */
    private void connectToNode(TopoNode node) {
        try {
            // 建立网络连接
            boolean connected = establishNodeConnection(node);

            if (connected) {
                connectedNodes.put(node.getNodeId(), String.format("http://%s:%d/long-poll", node.getHost(), node.getPort()));

                // 在拓扑网络中建立连接关系
                // TODO 暂时使用http
                if (config.getTopology().getUpstreamNodes().contains(node.getNodeId())) {
                    network.connectNodes(node.getNodeId(), nodeId, CommunicationType.HTTP); // 对方是上游
                } else {
                    network.connectNodes(nodeId, node.getNodeId(), CommunicationType.HTTP); // 对方是下游
                }

                System.out.println("成功连接到节点: " + node.getNodeId());
            }

        } catch (Exception e) {
            System.err.println("连接节点失败 " + node.getNodeId() + ": " + e.getMessage());
        }
    }

    /**
     * 建立节点连接
     */
    private boolean establishNodeConnection(TopoNode node) {
        // 这里实现实际的节点连接建立逻辑
        // 例如：建立TCP连接、HTTP长连接等
        return true; // 模拟连接成功
    }

    /**
     * 处理节点状态变化
     */
    private void handleNodeStateChange(TopoNode node, TopoNode.TopoNodeStatus newStatus, String reason) {
        if (!nodeId.equals(node.getNodeId())) {
            // 其他节点的状态变化
            if (newStatus == TopoNode.TopoNodeStatus.OFFLINE) {
                // 移除连接
                connectedNodes.remove(node.getNodeId());
                System.out.println("节点连接断开: " + node.getNodeId() + " - " + reason);
            }
        }
    }

    /**
     * 从配置创建节点
     */
    private TopoNode createTopoNodeFromConfig(NodeConfig config) {
        TopoNode node = new TopoNode(
            config.getNode().getNodeId(),
            config.getNode().getHost(),
            config.getNode().getPort(),
            config.getNode().getNodeType()
        );

        return node;
    }
    /**
     * 获取当前连接数
     */
    private int getCurrentConnections() {
        // 这里实现实际的连接数统计
        return connectedNodes.size(); // 模拟连接数
    }
    //
    /**
     * TODO 发送心跳到其他节点
     */
    private void sendHeartbeatToNode(String nodeId) {
        // 实现向其他节点发送心跳的逻辑
    }

    public String getNodeId() { return nodeId; }
    public boolean isRunning() { return running; }
    public NodeConfig getConfig() { return config; }
}
