/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package communicate.http;

import com.footprintcat.frostiot.common.dto.master.ConnectionInfoDTO;
import com.footprintcat.frostiot.topology.communicate.CommunicationTool;
import com.footprintcat.frostiot.common.enums.CommunicationTypeEnum;
import com.footprintcat.frostiot.topology.communicate.http.HttpCommunicationTool;
import com.footprintcat.frostiot.topology.pojo.message.Message;
import com.footprintcat.frostiot.common.enums.MessageTypeEnum;

public class ClientAppTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- 客户端启动 ---");
        CommunicationTool client = new HttpCommunicationTool();

        ConnectionInfoDTO clientConfig = ConnectionInfoDTO.builder()
            .protocol(CommunicationTypeEnum.HTTP.getCode())
            .host("localhost")
            .port(8669)
            .build();

        client.init(clientConfig);

        Thread.sleep(1000);

        // 创建服务端的连接信息，用于获取其 URL
        ConnectionInfoDTO serverConfig = ConnectionInfoDTO.builder()
            .protocol(CommunicationTypeEnum.HTTP.getCode())
            .host("localhost")
            .port(8668)
            .build();

        String messageText = "你好，服务端！这是来自 '" + clientConfig.getId() + "' 的消息。";
        Message message = new Message(messageText, MessageTypeEnum.DEVICE_MESSAGE);

        System.out.println("准备向服务端 '" + serverConfig.getId() + "' 发送消息...");

        String clientUrl = String.format("http://%s:%d/message", clientConfig.getHost(), clientConfig.getPort());
        String serverUrl = String.format("http://%s:%d/message", serverConfig.getHost(), serverConfig.getPort());
        client.sendMessage(message, serverUrl, clientUrl);

        System.out.println("客户端 '" + clientConfig.getId() + "' (" + clientConfig.getProtocol() + ") 正在运行，监听端口 " + clientConfig.getPort() + "...");
        System.out.println("按 Ctrl+C 停止客户端。");

        Runtime.getRuntime().addShutdownHook(new Thread(client::shutdown));
        Thread.currentThread().join();
    }
}
