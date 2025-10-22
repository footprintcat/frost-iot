/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.communicate.http;

import com.footprintcat.frostiot.topology.communicate.CommunicationConfig;
import com.footprintcat.frostiot.topology.communicate.CommunicationTool;
import com.footprintcat.frostiot.topology.communicate.CommunicationType;

public class HttpCommunicationTool implements CommunicationTool {
    @Override
    public void init(CommunicationConfig config) {
        // 空实现：不执行任何初始化操作
        System.out.println("HTTP Tool 初始化...");
    }

    @Override
    public void sendMessage(String message, String target) {
        // 空实现：不发送任何消息
        System.out.println("HTTP Tool 发送消息.");
    }

    @Override
    public void shutdown() {
        System.out.println("HTTP Tool 连接关闭.");
    }

    @Override
    public CommunicationType getType() {
        return CommunicationType.HTTP;
    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
