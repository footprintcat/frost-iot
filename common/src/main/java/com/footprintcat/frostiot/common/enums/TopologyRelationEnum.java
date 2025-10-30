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
public enum TopologyRelationEnum {
    // 下级
    SUB("sub"),
    // 上级
    SUP("sup"),
    ;

    private final String code;

    TopologyRelationEnum(String code) {
        this.code = code;
    }

    public String getCode(TopologyRelationEnum direction) {
        return direction.getCode();
    }

    public static TopologyRelationEnum getByCode(String code) {
        TopologyRelationEnum[] values = TopologyRelationEnum.values();
        for (TopologyRelationEnum topologyRelation : values) {
            if (topologyRelation.getCode().equals(code)) return topologyRelation;
        }
        return null;
    }
}
