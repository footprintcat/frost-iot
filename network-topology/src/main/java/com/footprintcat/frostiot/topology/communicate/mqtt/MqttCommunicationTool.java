/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.communicate.mqtt;

import com.footprintcat.frostiot.common.dto.ConnectionInfoDTO;
import com.footprintcat.frostiot.topology.communicate.CommunicationTool;
import com.footprintcat.frostiot.common.enums.CommunicationTypeEnum;
import com.footprintcat.frostiot.topology.pojo.message.Message;

public class MqttCommunicationTool implements CommunicationTool {
    @Override
    public void init(ConnectionInfoDTO config) {
        System.out.println("MQTT Tool初始化...");
    }

    @Override
    public void sendMessage(Message message, String target, String replyToUrl) {
        System.out.println("MQTT Tool发送消息.");
    }

    @Override
    public void shutdown() {
        System.out.println("MQTT Tool关闭.");
    }

    @Override
    public CommunicationTypeEnum getType() {
        return CommunicationTypeEnum.MQTT;
    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
