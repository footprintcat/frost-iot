/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.footprintcat.frostiot.common.dto.SystemConfigDTO;
import com.footprintcat.frostiot.common.repository.master.ISystemConfigRepository;
import com.footprintcat.frostiot.core.springboot.entity.SystemConfig;
import com.footprintcat.frostiot.core.springboot.mapper.master.SystemConfigMapper;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigRepository extends ServiceImpl<SystemConfigMapper, SystemConfig> implements ISystemConfigRepository {


    @Override
    public void setConfigValue(String key, String value) {

    }

    @Override
    public String getConfigValue(String key) {
        return "";
    }

    @Override
    public SystemConfigDTO getDTO(String key) {
        return new SystemConfigDTO();
    }


}
