/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo.topo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 拓扑网络构建器
 */
public class TopoNetworkBuilder {

    /**
     * 构建网络
     * @param config
     * @return
     */
    public static TopoNetwork buildFromConfig(TopologyConfig config){
        TopoNetwork network = new TopoNetwork();

        // 1.创建所有节点
        createNodes(network, config.getNodes());

        // 2.建立连接关系
        establishConnections(network, config.getConnections());

        return network;
    }

    /**
     * 创建节点
     * @param network
     * @param nodeConfigs
     */
    private static void createNodes(TopoNetwork network, List<TopologyConfig.NodeConfig> nodeConfigs){
        if(Objects.isNull(nodeConfigs)) return;

        for (TopologyConfig.NodeConfig nodeConfig : nodeConfigs) {
            TopoNode node = new TopoNode(
                nodeConfig.getNodeId(),
                nodeConfig.getHost(),
                nodeConfig.getPort(),
                TopoNode.TopoNodeStatus.OFFLINE, // 初始状态为离线，等待实际连接
                TopoNodeType.valueOf(nodeConfig.getNodeType())
            );

            network.addNode(node);
        }
    }

    /**
     * 建立连接
     * @param network
     * @param connections
     */
    private static void establishConnections(TopoNetwork network, List<TopologyConfig.ConnectionConfig> connections) {
        if(Objects.isNull(connections)) return;

        for (TopologyConfig.ConnectionConfig connection : connections) {
            String sourceNodeId = connection.getSourceNode();
            String targetNodeId = connection.getTargetNode();

            // TODO 尝试连接
            network.connectNodes(sourceNodeId, targetNodeId);
            // if(!Objects.isNull(connection.getProperties())){
            //     setConnectionProperties(network, sourceNodeId, targetNodeId, connection.getProperties());
            // }
        }
    }

    /**
     * 设置连接信息
     * @param network
     * @param sourceNodeId
     * @param targetNodeId
     * @param properties
     */
    private static void setConnectionProperties(TopoNetwork network, String sourceNodeId,
                                                String targetNodeId, Map<String, Object> properties) {
        // 设置连接信息
    }
}
