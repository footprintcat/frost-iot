/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.adapter.sdk.enumeration;

import lombok.Getter;

/**
 * 设备消息类型枚举
 *
 * @since 2025-04-23
 */
@Getter
public enum DeviceMessageType implements IEnumeration {

    DATA("data", "数据上报"),
    STATUS("status", "状态"),
    COMMAND("command", "指令响应"),

    ;

    /**
     * 设备消息类型 code
     */
    final String code;

    /**
     * 设备消息类型 name
     */
    final String name;

    DeviceMessageType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
