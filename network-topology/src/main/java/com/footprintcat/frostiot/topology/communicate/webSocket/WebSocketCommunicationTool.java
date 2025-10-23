/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.communicate.webSocket;

import com.footprintcat.frostiot.topology.communicate.CommunicationTool;
import com.footprintcat.frostiot.topology.communicate.CommunicationType;
import com.footprintcat.frostiot.topology.pojo.ConnectInfo;

public class WebSocketCommunicationTool implements CommunicationTool {
    @Override
    public void init(ConnectInfo config) {
        System.out.println("WebSocket初始化...");
    }

    @Override
    public void sendMessage(String message, String target, String replyToUrl) {
        System.out.println("WebSocket发消息.");
    }

    @Override
    public void shutdown() {
        System.out.println("WebSocket关闭连接.");
    }

    @Override
    public CommunicationType getType() {
        return CommunicationType.WEBSOCKET;
    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
