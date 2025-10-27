/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package communicate.http;

import com.footprintcat.frostiot.topology.communicate.CommunicationType;
import com.footprintcat.frostiot.topology.communicate.http.HttpLongPollingTool;
import com.footprintcat.frostiot.topology.pojo.ConnectInfo;
import com.footprintcat.frostiot.topology.pojo.message.Message;
import com.footprintcat.frostiot.topology.pojo.message.MessageType;

public class LongPollingClientAppTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- 长轮询客户端启动 ---");

        // 初始化连接
        HttpLongPollingTool client = new HttpLongPollingTool();
        ConnectInfo clientConfig = ConnectInfo.builder()
            .type(CommunicationType.HTTP)
            .localId("long-poll-client")
            .host("localhost")
            .port(8071)
            .build();

        client.init(clientConfig);
        Thread.sleep(1000); // 等待客户端初始化完成

        // 服务端的长轮询地址
        String serverLongPollUrl = "http://localhost:8670/long-poll";

        System.out.println("\n--- 测试1: 发送消息并等待正常回信 ---");
        String messageText1 = "你好，服务器，请处理我的请求！";
        Message message1 = new Message(messageText1, MessageType.DEFAULT);
        String reply1 = client.sendAndWaitForReply(message1, serverLongPollUrl, 15);
        if (reply1 != null) {
            System.out.println("客户端成功收到回信: " + reply1);
        } else {
            System.out.println("客户端未收到回信或发生超时/错误。");
        }

        System.out.println("\n--- 测试2: 发送消息并等待超时 ---");
        // 服务端处理需要5秒，我们这里只等3秒，必然会超时
        String messageText2 = "你好，服务器，我只会等3秒！";
        Message message2 = new Message(messageText2, MessageType.DEFAULT);
        String reply2 = client.sendAndWaitForReply(message2, serverLongPollUrl, 3);
        if (reply2 != null) {
            System.out.println("客户端成功收到回信: " + reply2);
        } else {
            System.out.println("客户端未收到回信，符合预期的超时。");
        }

        System.out.println("\n客户端测试完成。");
        client.shutdown();
    }
}
