/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.enums;

import com.footprintcat.frostiot.common.dto.master.TopologyInfoDTO;
import lombok.Getter;

@Getter
public enum TopologyStatusEnum {
    // 已连接
    CONNECTED("已连接"),
    DISCONNECTED("已断开"),
    UNKNOWN("未知")
    ;

    private final String code;

    TopologyStatusEnum(String code) {
        this.code = code;
    }

    public String getCode(TopologyStatusEnum status) {
        return status.getCode();
    }

    public static TopologyStatusEnum getByCode(String code) {
        TopologyStatusEnum[] values = TopologyStatusEnum.values();
        for (TopologyStatusEnum status : values) {
            if (status.getCode().equals(code)) return status;
        }
        return null;
    }
}
