/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package communicate.websocket;

import com.footprintcat.frostiot.topology.communicate.CommunicationType;
import com.footprintcat.frostiot.topology.communicate.webSocket.WebSocketCommunicationTool;
import com.footprintcat.frostiot.topology.pojo.ConnectInfo;
import com.footprintcat.frostiot.topology.pojo.message.Message;
import com.footprintcat.frostiot.topology.pojo.message.MessageType;

import java.util.Scanner;

public class WebSocketClientAppTest {
    public static void main(String[] args) {
        System.out.println("--- WebSocket 客户端启动 ---");

        ConnectInfo config = ConnectInfo.builder()
            .type(CommunicationType.WEBSOCKET)
            .localId("ws-client-01")
            .host("localhost")
            .port(9002)
            .build();

        WebSocketCommunicationTool client = new WebSocketCommunicationTool(config);

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
            Message message = new Message(messageText, MessageType.DEFAULT);
            client.sendMessage(message, serverUri, null);
        }

        scanner.close();
        client.shutdown();
    }
}
