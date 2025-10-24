/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo.topo;

public interface NodeStateListener {
    /**
     * 节点状态变化回调
     */
    void onNodeStateChanged(TopoNode node, TopoNode.TopoNodeStatus newStatus, String reason);

    /**
     * 网络稳定性变化回调
     */
    void onNetworkStabilityChanged(double stability, String message);

    /**
     * 连接关系变化回调
     */
    default void onConnectionChanged(String sourceNode, String targetNode, boolean connected, String reason) {
        // 默认空实现
    }
}
