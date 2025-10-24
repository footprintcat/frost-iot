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

import java.util.Scanner;

public class WebSocketServerAppTest {
    public static void main(String[] args) {
        System.out.println("--- WebSocket 服务端启动 ---");

        ConnectInfo serverConfig = ConnectInfo.builder()
            .type(CommunicationType.WEBSOCKET)
            .localId("ws-server-01")
            .host("localhost")
            .port(9001)
            .build();

        WebSocketCommunicationTool server = new WebSocketCommunicationTool(serverConfig);
        server.init(serverConfig);

        Scanner scanner = new Scanner(System.in);
        System.out.println("输入消息并回车，可向所有客户端广播。输入 'quit' 退出。");
        while (true) {
            String input = scanner.nextLine();
            if ("quit".equalsIgnoreCase(input)) {
                break;
            }
            server.sendMessage("服务端广播：" + input, null, null);
        }

        scanner.close();
        server.shutdown();
    }
}
