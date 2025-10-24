/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo.topo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;

public class NodeConfigLoader {
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    /**
     * 从资源文件加载配置
     */
    public static NodeConfig loadFromResources(String resourcePath) throws Exception {
        try (InputStream is = TopologyConfigLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            System.out.println("--- 读取资源文件 ---");
            if (is == null) {
                throw new IllegalArgumentException("未找到文件: " + resourcePath);
            }

            return YAML_MAPPER.readValue(is, NodeConfig.class);
        }
    }
}
