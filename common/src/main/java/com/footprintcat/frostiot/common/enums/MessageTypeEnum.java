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
public enum MessageTypeEnum {

    // 心跳
    HEARTBEAT("heart_beat"),
    // 连接事件 （建立连接、断开连接、结束连接）
    CONNECTION_EVENT("connection_event"),
    // 拓扑结构改变事件 （广播）
    TOPOLOGICAL_STRUCTURE_CHANGE("topological_structure_change"),
    // 设备报文
    DEVICE_MESSAGE("device_message"),
    // 指令下发
    SEND_ORDER("send_order"),
    ;

    private final String code;

    MessageTypeEnum(String code) {
        this.code = code;
    }

    public String getCode(MessageTypeEnum  messageTypeEnum) {
        return messageTypeEnum.getCode();
    }

    public static MessageTypeEnum getByCode(String code) {
        MessageTypeEnum[] values = MessageTypeEnum.values();
        for (MessageTypeEnum messageTypeEnum : values) {
            if (Objects.equals(messageTypeEnum.getCode(), code)) {
                return messageTypeEnum;
            }
        }
        return null;
    }

}
