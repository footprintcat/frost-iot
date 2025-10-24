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

public class LongPollingServerApp {
    public static void main(String[] args) throws InterruptedException {
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
}
