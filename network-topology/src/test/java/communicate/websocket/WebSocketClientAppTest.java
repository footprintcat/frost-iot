/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package communicate.websocket;

import com.footprintcat.frostiot.common.dto.master.ConnectionInfoDTO;
import com.footprintcat.frostiot.common.dto.master.SystemConfigDTO;
import com.footprintcat.frostiot.common.repository.master.ISystemConfigRepository;
import com.footprintcat.frostiot.common.enums.CommunicationTypeEnum;
import com.footprintcat.frostiot.topology.communicate.webSocket.WebSocketCommunicationTool;
import com.footprintcat.frostiot.topology.pojo.message.Message;
import com.footprintcat.frostiot.common.enums.MessageTypeEnum;

import java.util.Scanner;

public class WebSocketClientAppTest {
    public static void main(String[] args) {
        System.out.println("--- WebSocket 客户端启动 ---");

        ConnectionInfoDTO config = ConnectionInfoDTO.builder()
            .protocol(CommunicationTypeEnum.WEBSOCKET.getCode())
            .host("localhost")
            .port(9002)
            .build();


        WebSocketCommunicationTool client = new WebSocketCommunicationTool(config, new ISystemConfigRepository() {
            @Override
            public void setConfig(String owner, String key, String value, Long expireTimestamp) {

            }

            @Override
            public String getConfigValue(String key) {
                if (key == "NODE_ID") {
                    return "gossip-72";
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

        // 连接到服务端
        String serverUri = "ws://localhost:9001";
        client.connectToServer(serverUri);

        // 等待

        Scanner scanner = new Scanner(System.in);
        System.out.println("输入消息并回车，可发送给服务端。输入 'quit' 退出。");
        while (true) {
            String input = scanner.nextLine();
            if ("quit".equalsIgnoreCase(input)) {
                break;
            }
            String messageText = "客户端消息:" + input;
            Message message = new Message(messageText, MessageTypeEnum.DEVICE_MESSAGE);
            client.sendMessage(message, serverUri, null);
        }

        scanner.close();
        client.shutdown();
    }
}
