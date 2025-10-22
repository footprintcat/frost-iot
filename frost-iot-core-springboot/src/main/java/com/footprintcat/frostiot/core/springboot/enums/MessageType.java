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
