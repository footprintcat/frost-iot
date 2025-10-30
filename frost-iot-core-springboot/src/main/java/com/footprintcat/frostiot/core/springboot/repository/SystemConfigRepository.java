/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.footprintcat.frostiot.common.dto.master.SystemConfigDTO;
import com.footprintcat.frostiot.common.repository.master.ISystemConfigRepository;
import com.footprintcat.frostiot.common.utils.StringUtils;
import com.footprintcat.frostiot.core.springboot.entity.SystemConfig;
import com.footprintcat.frostiot.core.springboot.mapper.master.SystemConfigMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigRepository extends ServiceImpl<SystemConfigMapper, SystemConfig> implements ISystemConfigRepository {

    private static final String defaultOwner = "core-springboot";

    public String getConfigValue(String config) {
        SystemConfigDTO systemConfig = getConfig(config);
        return systemConfig == null ? null : systemConfig.getConfigValue();
    }

    public Integer getConfigValueInteger(String config) {
        String configValue = getConfigValue(config);
        return StringUtils.isEmpty(configValue) ? null : Integer.parseInt(configValue);
    }

    public Double getConfigValueDouble(String config) {
        String configValue = getConfigValue(config);
        return StringUtils.isEmpty(configValue) ? null : Double.parseDouble(configValue);
    }

    public Long getConfigValueLong(String config) {
        String configValue = getConfigValue(config);
        return StringUtils.isEmpty(configValue) ? null : Long.parseLong(configValue);
    }

    public Boolean getConfigValueBoolean(String config) {
        String configValue = getConfigValue(config);
        return configValue == null ? null : "1".equals(configValue);
    }

    public boolean getConfigValueBooleanValue(String config) {
        String configValue = getConfigValue(config);
        return "1".equals(configValue);
    }

    @Override
    public void setConfig(@NotNull String owner, @NotNull String key, String value, @Nullable Long expireTimestamp) {
        // 先删后加
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setOwner(owner);
        systemConfig.setConfigKey(key);
        systemConfig.setConfigValue(value);
        systemConfig.setExpireTimestamp(expireTimestamp);

        // 删除旧配置
        removeConfig(owner, key);

        this.save(systemConfig);
    }

    @Override
    public SystemConfigDTO getConfig(String config) {
        SystemConfig systemConfig = getConfig(defaultOwner, config);
        SystemConfigDTO dto = SystemConfig.toDTO(systemConfig);
        return dto;
    }

    @Override
    public SystemConfigDTO getDTO() {
        return null;
    }

    public SystemConfig getConfig(String owner, String key) {
        SystemConfig systemConfig = this.lambdaQuery()
            .eq(SystemConfig::getOwner, owner)
            .eq(SystemConfig::getConfigKey, key)
            .one();
        if (systemConfig == null) {
            return null;
        }
        if (systemConfig.getExpireTimestamp() != null && systemConfig.getExpireTimestamp() < System.currentTimeMillis()) {
            // 配置已过期
            baseMapper.deleteById(systemConfig);
            return null;
        }
        return systemConfig;
    }

    /**
     * 配置赋值
     *
     * @param config
     * @param value
     */
    public void setConfig(String config, String value) {
        setConfig(defaultOwner, config, value, null);
    }

    public void setConfigBoolean(@NotNull String config, @NotNull Boolean value) {
        setConfig(defaultOwner, config, value ? "1" : "0", null);
    }

    public void setConfigDouble(String config, double value) {
        setConfig(defaultOwner, config, String.valueOf(value), null);
    }

    public void setConfigLong(String config, long value) {
        setConfig(defaultOwner, config, String.valueOf(value), null);
    }

    public void setConfigLong(String owner, String config, long value) {
        setConfig(owner, config, String.valueOf(value), null);
    }

    /**
     * 删除配置
     *
     * @param owner
     * @param config
     */
    public void removeConfig(@NotNull String owner, @NotNull String config) {
        LambdaQueryWrapper<SystemConfig> qw = new LambdaQueryWrapper<>();
        qw.eq(SystemConfig::getConfigKey, config);
        qw.eq(SystemConfig::getOwner, owner);
        baseMapper.delete(qw);
    }

    public void removeConfig(@NotNull String config) {
        removeConfig(defaultOwner, config);
    }
}
