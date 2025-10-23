/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo.topo;

import com.footprintcat.frostiot.topology.pojo.ConnectInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class TopoNode {
    public enum TopoNodeStatus{
        STABLE,
        UNSTABLE,
        OFFLINE
    }
    /**
     * 节点唯一标识
     */
    private final String nodeId;

    /**
     * 节点地址
     */
    private String host;

    /**
     * 节点端口
     */
    private int port;

    /**
     * 节点类型
     */
    private TopoNodeType nodeType;

    /**
     * 节点状态
     */
    private TopoNodeStatus nodeStatus;

    /**
     * 前置所有节点（消息来源方向）
     */
    private final Set<String> upstreamNodes = new HashSet<>();

    /**
     * 后继所有节点（消息转发方向）
     */
    private final Set<String> downstreamNodes = new HashSet<>();

    /**
     * 邻居节点
     */
    private final Map<String, ConnectInfo> neighborNodes = new ConcurrentHashMap<>();

    public TopoNode(String nodeId, String host, int port, TopoNodeStatus nodeStatus, TopoNodeType nodeType) {
        this.nodeId = nodeId;
        this.host = host;
        this.port = port;
        this.nodeStatus = nodeStatus;
        this.nodeType = nodeType;
    }

    // 添加邻居节点
    public void addNeighborNode(String nodeId, ConnectInfo connectInfo) {
        this.neighborNodes.put(nodeId, connectInfo);
    }

    // 添加上游节点
    public void addUpstreamNode(String nodeId) {
        upstreamNodes.add(nodeId);
    }

    // 添加下游节点
    public void addDownstreamNode(String nodeId) {
        downstreamNodes.add(nodeId);
    }

    // 移除连接
    public void removeUpstreamNode(String nodeId) {
        upstreamNodes.remove(nodeId);
        neighborNodes.remove(nodeId);
    }

    public void removeDownstreamNode(String nodeId) {
        downstreamNodes.remove(nodeId);
        neighborNodes.remove(nodeId);
    }
}
