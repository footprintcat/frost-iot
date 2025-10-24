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
import com.footprintcat.frostiot.topology.pojo.ConnectInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 拓扑网络管理类
 */
public class TopoNetwork {

    private final String topologyId;
    private final Map<String, TopoNode> nodes = new ConcurrentHashMap<>();
    private final Object lock = new Object();

    public TopoNetwork(String topologyId) {
        this.topologyId = topologyId;
    }


    /**
     * 根据资源文件构建拓扑网络
     *
     * @return
     * @throws Exception
     */
    public static TopoNetwork initializeFromResource(String resourcePath) throws Exception {
        System.out.println("--- 初始化拓扑网络 ---");
        TopologyConfig topologyConfig = TopologyConfigLoader.loadFromResources(resourcePath);
        TopoNetwork network = createTopoNodeFromConfig(topologyConfig);

        return network;
    }

    private static TopoNetwork createTopoNodeFromConfig(TopologyConfig config) {
        TopoNetwork network = new TopoNetwork(config.getTopologyId());

        // TODO 设置其他属性
        return network;
    }

    public TopoNode getNode(String nodeId) {
        return nodes.get(nodeId);
    }

    public List<TopoNode> getNodes() {
        return nodes.values().stream().toList();
    }

    /**
     * 添加节点
     *
     * @param node
     * @return
     */
    public boolean addNode(TopoNode node) {
        synchronized (lock) {
            if (nodes.containsKey(node.getNodeId())) {
                return false;
            }

            nodes.put(node.getNodeId(), node);
            return true;
        }
    }

    /**
     * 移除节点
     *
     * @param nodeId
     * @return
     */
    public boolean removeNode(String nodeId) {
        synchronized (lock) {
            TopoNode node = nodes.remove(nodeId);
            if (Objects.isNull(node)) {
                return false;
            }

            // 从其他节点的关联关系移除该节点
            nodes.values().forEach(n -> {
                n.removeUpstreamNode(nodeId);
                n.removeDownstreamNode(nodeId);
            });
            return true;
        }
    }

    /**
     * 建立连接
     *
     * @param sourceNodeId
     * @param targetNodeId
     * @return
     */
    public boolean connectNodes(String sourceNodeId, String targetNodeId, CommunicationType connectionType) {
        synchronized (lock) {
            TopoNode sourceNode = nodes.get(sourceNodeId);
            TopoNode targetNode = nodes.get(targetNodeId);

            if (Objects.isNull(sourceNode) || Objects.isNull(targetNode)) {
                System.out.println("连接 " + sourceNodeId + " 至 " + targetNodeId + " 失败");
                return false;
            }

            Set<String> upstreamNodes = sourceNode.getUpstreamNodes();
            for (String nodeId : upstreamNodes) {
                TopoNode topoNode = nodes.get(nodeId);
                topoNode.addDownstreamNode(targetNodeId);
                targetNode.addUpstreamNode(nodeId);
            }

            sourceNode.addNeighborNode(
                targetNodeId,
                ConnectInfo.builder()
                    .type(connectionType)
                    .localId(sourceNodeId)
                    .targetId(targetNodeId)
                    .host(targetNode.getHost())
                    .port(targetNode.getPort())
                    .build()
            );
            targetNode.addNeighborNode(
                sourceNodeId,
                ConnectInfo.builder()
                    .type(connectionType)
                    .localId(targetNodeId)
                    .targetId(sourceNodeId)
                    .host(sourceNode.getHost())
                    .port(sourceNode.getPort())
                    .build()
            );
            sourceNode.addDownstreamNode(targetNodeId);
            targetNode.addUpstreamNode(sourceNodeId);
            System.out.println("连接 " + sourceNodeId + " 至 " + targetNodeId + ", " + sourceNodeId + " 上游节点" + sourceNode.getUpstreamNodes() + " 下游节点" + sourceNode.getDownstreamNodes());

            return true;
        }
    }

    /**
     * 断开连接
     *
     * @param nodeId1
     * @param nodeId2
     * @return
     */
    public boolean disconnectNodes(String nodeId1, String nodeId2) {
        synchronized (lock) {
            TopoNode node1 = nodes.get(nodeId1);
            TopoNode node2 = nodes.get(nodeId2);

            if (Objects.isNull(node1) || Objects.isNull(node2)) {
                return false;
            }

            node1.removeUpstreamNode(nodeId2);
            node1.removeDownstreamNode(nodeId2);
            node2.removeUpstreamNode(nodeId1);
            node2.removeDownstreamNode(nodeId1);
            return true;
        }
    }
}
