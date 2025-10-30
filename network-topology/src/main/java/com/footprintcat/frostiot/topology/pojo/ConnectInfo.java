/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo;

import com.footprintcat.frostiot.common.enums.CommunicationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ConnectInfo {
    // 连接类型
    CommunicationTypeEnum type;
    // 发送id
    String localId;
    // 接收id
    String targetId;

    // 连接信息
    // host+port or url
    String host;
    Integer port;
    String url;

    public static class ConnectInfoBuilder {
        public ConnectInfo build() {
            if (this.url == null || this.url.isEmpty()) {
                // 根据 type 生成不同前缀的 URL 默认使用 http 协议
                if (this.type == CommunicationTypeEnum.HTTP) {
                    this.url = String.format("http://%s:%d/message", host, port);
                } else {
                    // 默认暂时http
                    this.url = String.format("http://%s:%d/message", host, port);
                }
            }
            return new ConnectInfo(type, localId, targetId, host, port, url);
        }
    }
}
