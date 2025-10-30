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

public class ServerAppTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- 服务端启动 ---");
        CommunicationTool server = new HttpCommunicationTool();

        ConnectionInfoDTO config = ConnectionInfoDTO.builder()
            .protocol(CommunicationTypeEnum.HTTP.getCode())
            .host("localhost")
            .port(8668)
            .build();

        server.init(config);

        System.out.println("服务端 '" + config.getId() + "' (" + config.getProtocol() + ") 正在运行，监听端口 " + config.getPort() + "...");
        System.out.println("按 Ctrl+C 停止服务端。");

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        Thread.currentThread().join();
    }
}
