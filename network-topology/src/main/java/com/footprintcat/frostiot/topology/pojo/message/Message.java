/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo.message;

import com.footprintcat.frostiot.common.enums.MessageTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Message implements Serializable {
    /**
     * 消息唯一标识
     */
    private final String id = UUID.randomUUID().toString();

    /**
     * 消息内容
     */
    private final String payload;

    /**
     * 消息类型
     */
    private final MessageTypeEnum type;

    /**
     * 当前节点
     */
    private String currentNodeId;

    /**
     * 已访问的节点路径
     */
    private final List<String> visitedNodeIds = new ArrayList<>();

    /**
     * 创建时间
     */
    private final long createdTime = System.currentTimeMillis();

    /**
     * 重试次数
     */
    private final AtomicInteger retryCount = new AtomicInteger(0);

    public Message(String payload, MessageTypeEnum type) {
        this.payload = payload;
        this.type = type;
    }
}
