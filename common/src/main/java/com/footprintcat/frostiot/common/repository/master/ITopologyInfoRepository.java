/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.repository.master;

import com.footprintcat.frostiot.common.dto.master.TopologyInfoDTO;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ITopologyInfoRepository {

    /**
     * 更新或修改
     */
    void saveOrUpdate(TopologyInfoDTO topologyInfoDTO);

    /**
     * 标记连接状态
     */
    boolean setConnectStatus(String nodeId, String targetNodeId, String status);

    /**
     * 删除连接记录
     */
    boolean delTopologyInfo(String targetNodeId);

    /**
     * 根据nodeId和targetNodeId查询
     */
    TopologyInfoDTO getByNodeIdAndTargetNodeId(@NotNull String nodeId, @NotNull String targetNodeId);

    /**
     * 查询当前节点的上级/下级节点（相邻）
     */
    List<TopologyInfoDTO> getSubOrSupNodes(@NotNull String nodeId, @NotNull String direction);

    /**
     * 根据nodeId查询相邻上/下游并根据其相邻上/下游查询所有上/下游
     */
    List<TopologyInfoDTO> getAllSubsOrSupsByNodeId(@NotNull String nodeId, @NotNull String direction);

    /**
     * 批量存入连接信息
     */
    void saveBatchTopologyInfo(List<TopologyInfoDTO> topologyInfoDTOList);

    /**
     * 清空该表
     */
    void clear();
}
