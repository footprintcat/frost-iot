package com.footprintcat.frostiot.adapter.enumeration;

import lombok.Getter;

/**
 * 设备状态枚举
 *
 * @since 2025-04-23
 */
@Getter
public enum DeviceOnlineStatus implements IEnumeration {

    ONLINE("online", "在线"),
    OFFLINE("offline", "离线"),
    ERROR("error", "错误"),
    UNCERTAIN("uncertain", "不确定"),
    UNKNOWN("unknown", "未知"),
    // WARNING("warning", "预警中"),

    // UNCERTAIN：尚未判断是否在线；UNKNOWN：已明确 通过已有数据无法判断设备是否在线

    ;

    /**
     * 设备状态 code
     */
    final String code;

    /**
     * 设备状态 name
     */
    final String name;

    DeviceOnlineStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
