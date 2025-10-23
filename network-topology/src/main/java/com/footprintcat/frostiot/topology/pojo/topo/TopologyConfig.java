/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.topology.pojo.topo;

import com.footprintcat.frostiot.topology.communicate.CommunicationType;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class TopologyConfig {

    private List<NodeConfig> nodes;
    private List<ConnectionConfig> connections;

    @Getter
    public static class NodeConfig {
        private String nodeId;
        private String nodeType;
        private String host;
        private Integer port;
    }

    @Getter
    public static class ConnectionConfig {
        private String sourceNode;
        private String targetNode;
        private CommunicationType connectionType;
        private Map<String, Object> properties;
    }
}
