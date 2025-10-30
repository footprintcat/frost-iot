/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.service;

import com.footprintcat.frostiot.common.enums.NodeTypeEnum;
import com.footprintcat.frostiot.core.springboot.internal.CurrentNodeInfo;
import com.footprintcat.frostiot.core.springboot.internal.FrostIotCoreModuleInfo;
import com.footprintcat.frostiot.core.springboot.repository.SystemConfigRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DatabaseInitConfig {

    public static final String NODE_TYPE = NodeTypeEnum.GOSSIP.getCode();

    @Resource
    private SystemConfigRepository systemConfigRepository;

    @Bean
    public CurrentNodeInfo nodeInit() {
        // 读取系统配置版本号
        FrostIotCoreModuleInfo moduleInfo = FrostIotCoreModuleInfo.getInstance();
        Long databaseVersion = moduleInfo.getDatabaseVersion();

        // 查询节点配置初值，为空则初始化
        String nodeId = systemConfigRepository.getConfigValue("NODE_ID");
        String nodeType = systemConfigRepository.getConfigValue("NODE_TYPE");
        boolean isNodeInit = systemConfigRepository.getConfigValueBooleanValue("IS_NODE_INIT");
        if (nodeId == null || nodeType == null || !isNodeInit) {
            String randomNodeId = NODE_TYPE + "_" + (int) (Math.random() * 100);
            systemConfigRepository.setConfig("NODE_TYPE", NODE_TYPE);
            systemConfigRepository.setConfigLong("DB_VERSION", databaseVersion);
            systemConfigRepository.setConfig("NODE_ID", randomNodeId);
            systemConfigRepository.setConfigLong("IS_NODE_INIT", 1);

            nodeId = randomNodeId;
            nodeType = NODE_TYPE;
        }

        CurrentNodeInfo currentNodeInfo = new CurrentNodeInfo(nodeId, nodeType);
        // currentNodeInfo.setNodeId(nodeId);
        // currentNodeInfo.setNodeType(nodeType);
        log.info("nodeInit: nodeId={}, nodeType={}", nodeId, nodeType);
        log.info("currentNodeInfo: hashCode={}", currentNodeInfo.hashCode());

        return currentNodeInfo;
    }

}
