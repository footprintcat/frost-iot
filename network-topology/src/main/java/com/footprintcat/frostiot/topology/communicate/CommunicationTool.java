/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.communicate;

import com.footprintcat.frostiot.common.dto.master.ConnectionInfoDTO;
import com.footprintcat.frostiot.common.enums.CommunicationTypeEnum;
import com.footprintcat.frostiot.topology.pojo.message.Message;

public interface CommunicationTool {

    void init(ConnectionInfoDTO config);

    // void sendMessage(String message, String target);
    void sendMessage(Message message, String target, String replyToUrl);

    void shutdown();

    CommunicationTypeEnum getType();

    boolean isConnected();

    // ==================== 事件回调方法 ====================
    /**
     * 接收到消息时回调
     */
    default void onReceive(String message, String source) {
        System.out.println("[" + getType() + "] onReceive: 收到来自 '" + source + "' 的消息: " + message);
    }

    /**
     * 连接建立时回调
     */
    default void onConnected(){
        System.out.println("[" + getType() + "] onConnect: 连接已建立。");
    }

    /**
     * 连接关闭时回调
     */
    default void onConnectClose(Throwable cause) {
        if (cause == null) {
            System.out.println("[" + getType() + "] onConnectClose: 连接已正常关闭。");
        } else {
            System.err.println("[" + getType() + "] onConnectClose: 连接因异常关闭: " + cause.getMessage());
        }
    }

}
