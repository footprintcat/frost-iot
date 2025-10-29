/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.service;

import com.footprintcat.frostiot.common.dto.SystemConfigDTO;
import com.footprintcat.frostiot.common.enums.NodeTypeEnum;
import com.footprintcat.frostiot.core.springboot.internal.FrostIotCoreModuleInfo;
import com.footprintcat.frostiot.core.springboot.repository.SystemConfigRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigService {

    public static final String NODE_TYPE = NodeTypeEnum.GOSSIP.getCode();

    @Resource
    private SystemConfigRepository systemConfigRepository;

    @PostConstruct
    public void initSystemConfig() {
        // 读取系统配置版本号
        FrostIotCoreModuleInfo moduleInfo = FrostIotCoreModuleInfo.getInstance();
        Long databaseVersion = moduleInfo.getDatabaseVersion();

        // 查询节点配置初值，为空则初始化
        SystemConfigDTO nodeIdConfig = systemConfigRepository.getConfig("NODE_ID");
        SystemConfigDTO nodeTypeConfig = systemConfigRepository.getConfig("NODE_TYPE");
        SystemConfigDTO isNodeInitConfig = systemConfigRepository.getConfig("IS_NODE_INIT");
        if (nodeIdConfig == null || nodeTypeConfig == null || isNodeInitConfig == null || "0".equals(isNodeInitConfig.getConfigValue())
        ) {
            systemConfigRepository.setConfig("NODE_TYPE", NODE_TYPE);
            systemConfigRepository.setConfigLong("DB_VERSION", databaseVersion);
            systemConfigRepository.setConfig("NODE_ID", NODE_TYPE + "_" + (int) (Math.random() * 100));
            systemConfigRepository.setConfigLong("IS_NODE_INIT", 1);
        }
    }

}
