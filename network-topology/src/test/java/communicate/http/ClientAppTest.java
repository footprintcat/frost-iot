/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package communicate.http;import com.footprintcat.frostiot.topology.communicate.CommunicationTool;
import com.footprintcat.frostiot.topology.communicate.CommunicationType;
import com.footprintcat.frostiot.topology.communicate.http.HttpCommunicationTool;
import com.footprintcat.frostiot.topology.pojo.ConnectInfo;

public class ClientAppTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- 客户端启动 ---");
        CommunicationTool client = new HttpCommunicationTool();

        ConnectInfo clientConfig = ConnectInfo.builder()
            .type(CommunicationType.HTTP)
            .localId("client-node-01")
            .host("localhost")
            .port(8669)
            .build();

        client.init(clientConfig);

        Thread.sleep(1000);

        // 创建服务端的连接信息，用于获取其 URL
        ConnectInfo serverConfig = ConnectInfo.builder()
            .type(CommunicationType.HTTP)
            .localId("server-node-01")
            .host("localhost")
            .port(8668)
            .build();

        String message = "你好，服务端！这是来自 '" + clientConfig.getLocalId() + "' 的消息。";
        System.out.println("准备向服务端 '" + serverConfig.getLocalId() + "' 发送消息...");

        client.sendMessage(message, serverConfig.getUrl(), clientConfig.getUrl());

        System.out.println("客户端 '" + clientConfig.getLocalId() + "' (" + clientConfig.getType() + ") 正在运行，监听端口 " + clientConfig.getPort() + "...");
        System.out.println("按 Ctrl+C 停止客户端。");

        Runtime.getRuntime().addShutdownHook(new Thread(client::shutdown));
        Thread.currentThread().join();
    }
}
