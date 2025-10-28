/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.dto;

import lombok.Data;

/**
 * 运行时拓扑信息
 */
@Data
public class TopologyInfoDTO {

    public enum Direction {
        SUB,    // 下级
        SUP     // 上级
    }

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 节点ID
     */
    private String nodeId;

    /**
     * 方向（sub：下级；sup：上级）
     */
    private Direction direction;

    /**
     * 间隔节点数
     */
    private Integer interval;

    /**
     * 目标节点
     */
    private String targetNodeId;
}
