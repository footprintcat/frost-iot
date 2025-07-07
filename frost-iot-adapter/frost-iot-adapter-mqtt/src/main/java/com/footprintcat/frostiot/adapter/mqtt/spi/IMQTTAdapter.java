package com.footprintcat.frostiot.adapter.mqtt.spi;

import com.footprintcat.frostiot.adapter.sdk.spi.IAdapter;

public interface IMQTTAdapter extends IAdapter {

    void connect();
}
