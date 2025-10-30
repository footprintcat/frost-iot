/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.service;

import com.footprintcat.frostiot.common.dto.master.ClientInfoDTO;
import com.footprintcat.frostiot.common.enums.CommunicationTypeEnum;
import com.footprintcat.frostiot.common.enums.MessageTypeEnum;
import com.footprintcat.frostiot.common.internal.ICurrentNodeInfo;
import com.footprintcat.frostiot.topology.communicate.webSocket.WebSocketCommunicationTool;
import com.footprintcat.frostiot.topology.pojo.message.Message;
import com.footprintcat.frostiot.topology.topo.TopologyLifeCircleEvent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ServerService {

    @Resource
    private ICurrentNodeInfo currentNodeInfo;
    @Resource
    private TopologyLifeCircleEvent topologyLifeCircleEvent;

    @PostConstruct
    public void initConnection() {
        createServer();
    }

    public void createServer() {
        System.out.println("--- WebSocket 服务端启动 ---");

        ClientInfoDTO serverConfig = ClientInfoDTO.builder()
            .protocol(CommunicationTypeEnum.WEBSOCKET.getCode())
            .host("localhost")
            .port(9001)
            .build();


        WebSocketCommunicationTool server = new WebSocketCommunicationTool(serverConfig, currentNodeInfo, topologyLifeCircleEvent);
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
