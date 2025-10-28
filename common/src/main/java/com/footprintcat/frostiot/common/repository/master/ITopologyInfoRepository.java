/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.repository.master;

import com.footprintcat.frostiot.common.dto.TopologyInfoDTO;

import java.util.List;

public interface ITopologyInfoRepository {

    void setTopologyInfo(TopologyInfoDTO topologyInfoDTO);

    TopologyInfoDTO getTopologyInfo(Long id);

    /**
     * 根据nodeId和targetNodeId查询
     */
    TopologyInfoDTO getByNodeIdAndTargetNodeId(String nodeId, String targetNodeId);

    /**
     * 查询当前节点的上级/下级节点
     */
    List<TopologyInfoDTO> getSubOrSupNodes(String nodeId, String direction);
}
