/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.service;

import com.footprintcat.frostiot.core.springboot.internal.CurrentNodeInfo;
import com.footprintcat.frostiot.core.springboot.repository.TopologyInfoRepository;
import com.footprintcat.frostiot.topology.topo.TopologyLifeCircleEvent;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Configuration
@AutoConfigureAfter(DatabaseInitConfig.class)
public class TopologyConfig {

    @Bean
    public TopologyLifeCircleEvent topologyLifeCircleEvent(TopologyInfoRepository topologyInfoRepository, CurrentNodeInfo currentNodeInfo) {
        Assert.isTrue(currentNodeInfo.getNodeId() != null, "currentNodeInfo.getNodeId() must not be null");
        Assert.isTrue(currentNodeInfo.getNodeType() != null, "currentNodeInfo.getNodeType() must not be null");

        return new TopologyLifeCircleEvent(topologyInfoRepository, currentNodeInfo);
    }

}
