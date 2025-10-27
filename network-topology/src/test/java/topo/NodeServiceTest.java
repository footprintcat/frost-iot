/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package topo;

import com.footprintcat.frostiot.topology.communicate.CommunicationType;
import com.footprintcat.frostiot.topology.communicate.http.HttpLongPollingTool;
import com.footprintcat.frostiot.topology.pojo.ConnectInfo;
import com.footprintcat.frostiot.topology.pojo.message.Message;
import com.footprintcat.frostiot.topology.pojo.message.MessageType;
import com.footprintcat.frostiot.topology.pojo.topo.NodeService;
import com.footprintcat.frostiot.topology.pojo.topo.TopoNetwork;
import com.footprintcat.frostiot.topology.pojo.topo.TopoNode;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class NodeServiceTest {
    public static void main(String[] args) {
        try {
            TopoNetwork topoNetwork = TopoNetwork.initializeFromResource("topology.yml");
            NodeService service1 = new NodeService(topoNetwork, "gossip-1.yml");
            NodeService service2 = new NodeService(topoNetwork, "gossip-2.yml");
            NodeService service3 = new NodeService(topoNetwork, "gossip-3.yml");
            NodeService service4 = new NodeService(topoNetwork, "gossip-4.yml");

            service1.start();
            service2.start();
            service3.start();
            service4.start();

            Thread.sleep(1000);

            // 发送消息节点
            TopoNode node = topoNetwork.getNode(service1.getNodeId());
            if (!Objects.isNull(node)) {
                Message message = new Message("你好，我是gossip-2", MessageType.DEFAULT);
                service2.sendMessage(message, String.format("http://%s:%d/long-poll", node.getHost(), node.getPort())); // http://localhost:8670/long-poll
            }
        } catch (Exception e) {
            System.err.println("节点服务启动失败: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGossip1() {
    }

    @Test
    public void testServer() throws InterruptedException {
        System.out.println("--- 长轮询服务端启动 ---");
        HttpLongPollingTool server = new HttpLongPollingTool();

        ConnectInfo config = ConnectInfo.builder()
            .type(CommunicationType.HTTP)
            .localId("long-poll-server")
            .host("localhost")
            .port(8670)
            .build();

        server.init(config);

        System.out.println("长轮询服务端 '" + config.getLocalId() + "' 正在运行，监听端口 " + config.getPort() + "...");

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        Thread.currentThread().join();
    }

    @Test
    public void testClient() throws InterruptedException {
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
