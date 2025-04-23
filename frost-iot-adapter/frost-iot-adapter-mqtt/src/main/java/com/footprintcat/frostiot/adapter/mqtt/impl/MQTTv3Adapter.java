package com.footprintcat.frostiot.adapter.mqtt.impl;

import com.footprintcat.frostiot.adapter.mqtt.spi.IMQTTAdapter;
import lombok.Getter;

public class MQTTv3Adapter implements IMQTTAdapter {

    @Getter
    private final String name = "MQTTv3Adapter";

    @Getter
    private final int versionCode = 1;

    @Override
    public void connect() {

    }

}
