/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo.topo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class NodeStateManager {
    private final TopoNetwork network;
    private final ScheduledExecutorService scheduler;
    private final Map<String, NodeHealthInfo> healthInfoMap;
    private final List<NodeStateListener> listeners;
    private final AtomicBoolean running;

    // 配置参数
    // 心跳间隔(ms)
    private long heartbeatInterval = 30000;
    // 心跳超时时间(ms)
    private long heartbeatTimeout = 60000;
    // 最大重试次数
    private int maxRetryCount = 3;
    // 重试间隔(ms)
    private long retryInterval = 5000;
    // 网络稳定性阈值
    private double networkStabilityThreshold = 0.8;

    public NodeStateManager(TopoNetwork network) {
        this.network = network;
        this.scheduler = Executors.newScheduledThreadPool(4);
        this.healthInfoMap = new ConcurrentHashMap<>();
        this.listeners = new CopyOnWriteArrayList<>();
        this.running = new AtomicBoolean(false);
    }

    // 监听器管理
    public void addStateListener(NodeStateListener listener) {
        listeners.add(listener);
    }

    public void removeStateListener(NodeStateListener listener) {
        listeners.remove(listener);
    }

    /**
     * 启动状态管理器
     */
    public void start() {
        if (running.compareAndSet(false, true)) {
            // 启动心跳检测任务
            scheduler.scheduleAtFixedRate(this::checkHeartbeats,
                heartbeatInterval, heartbeatInterval, TimeUnit.MILLISECONDS);

            // 启动网络稳定性检测任务
            scheduler.scheduleAtFixedRate(this::checkNetworkStability,
                heartbeatInterval * 2, heartbeatInterval * 2, TimeUnit.MILLISECONDS);

            System.out.println("节点状态管理器已启动");
        }
    }

    /**
     * 停止状态管理器
     */
    public void stop() {
        if (running.compareAndSet(true, false)) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
            System.out.println("节点状态管理器已停止");
        }
    }

    /**
     * 节点上线处理
     */
    public boolean nodeOnline(String nodeId, String host, int port) {
        TopoNode node = network.getNode(nodeId);
        if (node == null) {
            System.err.println("节点不存在: " + nodeId);
            return false;
        }

        synchronized (node) {
            // 更新节点信息
            node.setHost(host);
            node.setPort(port);
            node.setNodeStatus(TopoNode.TopoNodeStatus.ONLINE);
            node.setLastHeartbeat(LocalDateTime.now());
            // node.setCurrentConnections(0);

            // 初始化健康信息
            NodeHealthInfo healthInfo = new NodeHealthInfo(nodeId);
            healthInfo.setOnline(true);
            healthInfo.setLastSeen(LocalDateTime.now());
            healthInfoMap.put(nodeId, healthInfo);

            // 通知监听器
            notifyNodeStateChange(node, TopoNode.TopoNodeStatus.ONLINE, "节点上线");

            System.out.println("节点上线: " + nodeId + " [" + host + ":" + port + "]");
            return true;
        }
    }

    /**
     * 节点下线处理
     */
    public boolean nodeOffline(String nodeId, String reason) {
        TopoNode node = network.getNode(nodeId);
        if (node == null) {
            return false;
        }

        synchronized (node) {
            TopoNode.TopoNodeStatus oldStatus = node.getNodeStatus();
            node.setNodeStatus(TopoNode.TopoNodeStatus.OFFLINE);
            node.setLastHeartbeat(LocalDateTime.now());

            // 更新健康信息
            NodeHealthInfo healthInfo = healthInfoMap.get(nodeId);
            if (healthInfo != null) {
                healthInfo.setOnline(false);
                healthInfo.setLastSeen(LocalDateTime.now());
                healthInfo.incrementOfflineCount();
            }

            // 通知监听器
            notifyNodeStateChange(node, TopoNode.TopoNodeStatus.OFFLINE, reason);

            System.out.println("节点下线: " + nodeId + " - 原因: " + reason);
            return true;
        }
    }

    /**
     * 处理节点心跳
     */
    public boolean processHeartbeat(String nodeId, int currentConnections) {
        TopoNode node = network.getNode(nodeId);
        if (node == null) {
            return false;
        }

        synchronized (node) {
            LocalDateTime now = LocalDateTime.now();
            node.setLastHeartbeat(now);

            // 如果节点当前是离线状态，自动恢复为在线
            if (node.getNodeStatus() == TopoNode.TopoNodeStatus.OFFLINE) {
                node.setNodeStatus(TopoNode.TopoNodeStatus.ONLINE);
                notifyNodeStateChange(node, TopoNode.TopoNodeStatus.ONLINE, "心跳恢复");
            }

            // 更新健康信息
            NodeHealthInfo healthInfo = healthInfoMap.computeIfAbsent(nodeId,
                k -> new NodeHealthInfo(nodeId));
            healthInfo.setOnline(true);
            healthInfo.setLastSeen(now);
            healthInfo.incrementHeartbeatCount();

            return true;
        }
    }

    /**
     * 检查心跳超时
     */
    private void checkHeartbeats() {
        if (!running.get()) return;

        LocalDateTime now = LocalDateTime.now();
        List<TopoNode> nodes = new ArrayList<>(network.getNodes());

        for (TopoNode node : nodes) {
            if (node.getLastHeartbeat() == null) continue;

            long timeSinceLastHeartbeat = java.time.Duration.between(
                node.getLastHeartbeat(), now).toMillis();

            if (timeSinceLastHeartbeat > heartbeatTimeout) {
                // 心跳超时，标记为离线
                if (node.getNodeStatus() != TopoNode.TopoNodeStatus.OFFLINE) {
                    nodeOffline(node.getNodeId(),
                        "心跳超时 (" + timeSinceLastHeartbeat + "ms)");
                }
            }
        }
    }

    /**
     * 检查网络稳定性
     */
    private void checkNetworkStability() {
        if (!running.get()) return;

        int totalNodes = network.getNodes().size();
        if (totalNodes == 0) return;

        long onlineNodes = network.getNodes().stream()
            .filter(node -> node.getNodeStatus() == TopoNode.TopoNodeStatus.ONLINE)
            .count();

        double stability = (double) onlineNodes / totalNodes;

        if (stability < networkStabilityThreshold) {
            notifyNetworkStabilityChange(stability, "网络稳定性下降: " +
                String.format("%.2f", stability * 100) + "%");
        }
    }

    /**
     * 节点状态变化
     */
    private void notifyNodeStateChange(TopoNode node, TopoNode.TopoNodeStatus newStatus, String reason) {
        for (NodeStateListener listener : listeners) {
            try {
                listener.onNodeStateChanged(node, newStatus, reason);
            } catch (Exception e) {
                System.err.println("监听器通知失败: " + e.getMessage());
            }
        }
    }

    /**
     * 网络状态变化
     */
    private void notifyNetworkStabilityChange(double stability, String message) {
        for (NodeStateListener listener : listeners) {
            try {
                listener.onNetworkStabilityChanged(stability, message);
            } catch (Exception e) {
                System.err.println("网络稳定性监听器通知失败: " + e.getMessage());
            }
        }
    }
}
