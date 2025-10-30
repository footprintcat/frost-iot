/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.dto.master;

import com.footprintcat.frostiot.common.enums.NodeTypeEnum;
import com.footprintcat.frostiot.common.enums.TopologyRelationEnum;
import com.footprintcat.frostiot.common.enums.TopologyStatusEnum;
import lombok.Data;
import lombok.Getter;

/**
 * 运行时拓扑信息
 */
@Data
public class TopologyInfoDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 节点ID
     */
    private String nodeId;

    /**
     * 节点类型
     */
    private NodeTypeEnum nodeType;

    /**
     * 方向（sub：下级；sup：上级）
     */
    private TopologyRelationEnum relation;

    /**
     * 间隔节点数
     */
    private Integer interval;

    /**
     * 目标节点
     */
    private String targetNodeId;

    /**
     * 目标节点类型
     */
    private NodeTypeEnum targetNodeType;

    /**
     * 是否连接（否为临时断连）
     */
    private TopologyStatusEnum status;
}
