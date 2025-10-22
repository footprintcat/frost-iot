/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.communicate;

import java.util.HashMap;
import java.util.Map;

public class CommunicationConfig {
    private Map<String, Object> properties;

    public CommunicationConfig() {
        this.properties = new HashMap<>();
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public void setUrl(String url) {
        setProperty("url", url);
    }

    public void setPort(int port) {
        setProperty("port", port);
    }
}
