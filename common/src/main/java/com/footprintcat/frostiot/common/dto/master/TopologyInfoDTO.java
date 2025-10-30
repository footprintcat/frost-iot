/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.dto.master;

import lombok.Data;
import lombok.Getter;

/**
 * 运行时拓扑信息
 */
@Data
public class TopologyInfoDTO {

    @Getter
    public enum Direction {
        // 下级
        SUB("sub"),
        // 上级
        SUP("sup"),
        ;

        private final String code;

        Direction(String code) {
            this.code = code;
        }

        public String getCode(Direction direction) {
            return direction.getCode();
        }

        public static Direction getByCode(String code) {
            Direction[] values = Direction.values();
            for (Direction direction : values) {
                if (direction.getCode().equals(code)) return direction;
            }
            return null;
        }
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

    /**
     * 是否连接（否为临时断连）
     */
    private Boolean isConnected;
}
