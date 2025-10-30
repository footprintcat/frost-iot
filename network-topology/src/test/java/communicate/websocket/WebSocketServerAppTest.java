/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package communicate.websocket;

import com.footprintcat.frostiot.common.dto.ConnectionInfoDTO;
import com.footprintcat.frostiot.common.dto.SystemConfigDTO;
import com.footprintcat.frostiot.common.enums.CommunicationTypeEnum;
import com.footprintcat.frostiot.common.repository.master.ISystemConfigRepository;
import com.footprintcat.frostiot.topology.communicate.webSocket.WebSocketCommunicationTool;
import com.footprintcat.frostiot.topology.pojo.message.Message;
import com.footprintcat.frostiot.common.enums.MessageTypeEnum;

import java.util.Scanner;

public class WebSocketServerAppTest {
    public static void main(String[] args) {
        System.out.println("--- WebSocket 服务端启动 ---");

        ConnectionInfoDTO serverConfig = ConnectionInfoDTO.builder()
            .protocol(CommunicationTypeEnum.WEBSOCKET.getCode())
            .host("localhost")
            .port(9001)
            .build();


        WebSocketCommunicationTool server = new WebSocketCommunicationTool(serverConfig, new ISystemConfigRepository() {
            @Override
            public void setConfig(String owner, String key, String value, Long expireTimestamp) {

            }

            @Override
            public String getConfigValue(String key) {
                if (key == "NODE_ID") {
                    return "gossip-60";
                } else if (key == "NODE_TYPE") {
                    return "gossip";
                }
                return null;
            }

            @Override
            public SystemConfigDTO getConfig(String key) {
                return null;
            }

            @Override
            public SystemConfigDTO getDTO() {
                return null;
            }
        });
        server.init(serverConfig);

        Scanner scanner = new Scanner(System.in);
        System.out.println("输入消息并回车，可向所有客户端广播。输入 'quit' 退出。");
        while (true) {
            String input = scanner.nextLine();
            if ("quit".equalsIgnoreCase(input)) {
                break;
            }
            String messageText = "服务端广播:" + input;
            Message message = new Message(messageText, MessageTypeEnum.DEVICE_MESSAGE);
            server.sendMessage(message, null, null);
        }

        scanner.close();
        server.shutdown();
    }
}
