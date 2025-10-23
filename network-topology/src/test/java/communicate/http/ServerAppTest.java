/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package communicate.http;

import com.footprintcat.frostiot.topology.communicate.CommunicationTool;
import com.footprintcat.frostiot.topology.communicate.CommunicationType;
import com.footprintcat.frostiot.topology.communicate.http.HttpCommunicationTool;
import com.footprintcat.frostiot.topology.pojo.ConnectInfo;

public class ServerAppTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- 服务端启动 ---");
        CommunicationTool server = new HttpCommunicationTool();

        ConnectInfo config = ConnectInfo.builder()
            .type(CommunicationType.HTTP)
            .localId("server-node-01")
            .host("localhost")
            .port(8668)
            .build(); // 调用我们自定义的 build 方法

        server.init(config);

        System.out.println("服务端 '" + config.getLocalId() + "' (" + config.getType() + ") 正在运行，监听端口 " + config.getPort() + "...");
        System.out.println("按 Ctrl+C 停止服务端。");

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        Thread.currentThread().join();
    }
}
