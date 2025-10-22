/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.entity.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.footprintcat.frostiot.core.springboot.enums.MessageType;

import java.io.Serializable;

/**
 * @author nieqiurong
 */
public class Messages implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String message;

    private MessageType messageType;

    // @TableField(fill = FieldFill.INSERT)
    // private String createUser;
    //
    // @TableField(fill = FieldFill.UPDATE)
    // private String updateUser;

    public Messages() {
    }

    public Messages(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Messages(Long id, String message, MessageType messageType) {
        this.id = id;
        this.message = message;
        this.messageType = messageType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}
