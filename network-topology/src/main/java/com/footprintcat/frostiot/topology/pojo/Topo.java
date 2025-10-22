/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Topo {
    /**
     * 节点唯一标识
     */
    private final String id = UUID.randomUUID().toString();

    /**
     * 节点地址
     */
    private String url;

    /**
     * 节点端口
     */
    private int port;

    /**
     * 节点类型
     */
    private TopoNodeType type;

    /**
     * 节点状态
     */
    private TopoNodeStatus status;

    /**
     * 前置所有节点（消息来源方向）
     */
    private final Map<Topo, ConnectionState> predecessorNodes = new ConcurrentHashMap<>();

    /**
     * 后继所有节点（消息转发方向）
     */
    private final Map<Topo, ConnectionState> successorNodes = new ConcurrentHashMap<>();

    /**
     * 邻居节点
     */
    private final Set<Topo> ConnectionStates = new HashSet<>();

    public Topo(String url, int port, TopoNodeStatus status, TopoNodeType type) {
        this.url = url;
        this.port = port;
        this.type = type;
        this.status = status;
    }
}
