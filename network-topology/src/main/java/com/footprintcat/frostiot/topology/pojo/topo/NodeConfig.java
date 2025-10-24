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

/**
 * 单个节点配置文件映射实体
 */
public class NodeConfig {

    private NodeInfo node;
    // private ServiceConfig service;
    private TopologyConfig topology;
    // private DiscoveryConfig discovery;

    public static class NodeInfo {
        private String nodeId;
        private String nodeType;
        private String host;
        private int port;
        // private int servicePort;
        // private int maxConnections;
        // private List<String> supportedProtocols;
        // private Map<String, Object> metadata;

        // Getters and Setters
        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getNodeType() {
            return nodeType;
        }

        public void setNodeType(String nodeType) {
            this.nodeType = nodeType;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        // public int getServicePort() {
        //     return servicePort;
        // }
        //
        // public void setServicePort(int servicePort) {
        //     this.servicePort = servicePort;
        // }
        //
        // public int getMaxConnections() {
        //     return maxConnections;
        // }
        //
        // public void setMaxConnections(int maxConnections) {
        //     this.maxConnections = maxConnections;
        // }
        //
        // public List<String> getSupportedProtocols() {
        //     return supportedProtocols;
        // }
        //
        // public void setSupportedProtocols(List<String> supportedProtocols) {
        //     this.supportedProtocols = supportedProtocols;
        // }
        //
        // public Map<String, Object> getMetadata() {
        //     return metadata;
        // }
        //
        // public void setMetadata(Map<String, Object> metadata) {
        //     this.metadata = metadata;
        // }
    }

    public static class TopologyConfig {
        private List<String> upstreamNodes;
        private List<String> downstreamNodes;
        private Map<String, Object> connectionProperties;

        // Getters and Setters
        public List<String> getUpstreamNodes() { return upstreamNodes; }
        public void setUpstreamNodes(List<String> upstreamNodes) { this.upstreamNodes = upstreamNodes; }

        public List<String> getDownstreamNodes() { return downstreamNodes; }
        public void setDownstreamNodes(List<String> downstreamNodes) { this.downstreamNodes = downstreamNodes; }

        public Map<String, Object> getConnectionProperties() { return connectionProperties; }
        public void setConnectionProperties(Map<String, Object> connectionProperties) { this.connectionProperties = connectionProperties; }
    }

    // Getters and Setters
    public NodeInfo getNode() { return node; }
    public void setNode(NodeInfo node) { this.node = node; }

    public TopologyConfig getTopology() { return topology; }
    public void setTopology(TopologyConfig topology) { this.topology = topology; }
}
