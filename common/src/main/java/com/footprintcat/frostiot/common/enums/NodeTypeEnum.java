/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.enums;

import lombok.Getter;

@Getter
public enum NodeTypeEnum {
    ADAPTER("adapter"),
    GOSSIP("gossip"),
    SINK("sink"),
    ;

    private final String code;

    NodeTypeEnum(String code) {
        this.code = code;
    }

    public String getCode(NodeTypeEnum nodeTypeEnum) {
        return nodeTypeEnum.getCode();
    }

    public static NodeTypeEnum getByCode(String code) {
        NodeTypeEnum[] values = NodeTypeEnum.values();
        for (NodeTypeEnum nodeTypeEnum : values) {
            if (nodeTypeEnum.getCode().equals(code)) return nodeTypeEnum;
        }
        return null;
    }

}
