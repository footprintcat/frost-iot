package com.footprintcat.frostiot.adapter.entity.DeviceMessage;

import com.footprintcat.frostiot.adapter.enumeration.DeviceMessageType;
import com.footprintcat.frostiot.adapter.enumeration.DeviceOnlineStatus;
import lombok.Data;

import java.util.Map;

/**
 * 设备消息实体类
 *
 * @since 2025-04-23
 */
@Data
public class BaseMessage {

    // 设备标识

    /**
     * 设备唯一ID（如IMEI、MAC地址）
     */
    private String deviceId;

    /**
     * 产品型号（可选，用于多产品线区分）
     */
    private String productKey;

    // 消息元数据

    /**
     * 消息唯一标识（UUID或设备生成）
     */
    private String messageId;

    /**
     * 消息产生时间（设备端或服务端时间戳）
     */
    private long timestamp;

    /**
     * 设备消息类型枚举
     */
    private DeviceMessageType messageType;

    // 设备上报的数据内容（JSON/二进制/键值对）

    /**
     * 报文 payload
     */
    private Map<String, Object> payload;
    /**
     * 原始数据（如Hex字符串）
     */
    private String rawData;
    /**
     * 二进制协议数据
     */
    private byte[] binaryData;

    // 设备状态（可选）

    /**
     * 设备在线状态
     */
    private DeviceOnlineStatus onlineStatus;
    private int batteryLevel;            // 电量（0-100%）
    private String firmwareVersion;      // 固件版本

}
