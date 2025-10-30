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

import java.util.Objects;

@Getter
public enum CommunicationTypeEnum {
    HTTP("http"),
    MQTT("mqtt"),
    WEBSOCKET("websocket"),
    ;

    private String code;

    CommunicationTypeEnum(String code) {
        this.code = code;
    }

    public String getCode(CommunicationTypeEnum communicationTypeEnum) {
        return communicationTypeEnum.getCode();
    }

    public static CommunicationTypeEnum getByCode(String code) {
        CommunicationTypeEnum[] values = CommunicationTypeEnum.values();
        for (CommunicationTypeEnum communicationTypeEnum : values) {
            if (Objects.equals(communicationTypeEnum.getCode(), code)) {
                return communicationTypeEnum;
            }
        }
        return null;
    }
}
