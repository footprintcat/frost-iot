/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author nieqiurong
 */
public enum MessageType implements IEnum<String> {

    /**
     * 文字
     */
    TEXT("text","文字"),

    /**
     * 语音
     */
    VOICE("voice","语音")
    ;

    private final String code;
    private final String desc;

    MessageType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    @Override
    public String getValue() {
        return this.code;
    }


}
