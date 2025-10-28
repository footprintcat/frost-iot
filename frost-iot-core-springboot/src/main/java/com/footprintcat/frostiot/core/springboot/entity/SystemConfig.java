/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.footprintcat.frostiot.common.dto.SystemConfigDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SystemConfig  {

    @Schema(description = "雪花id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "键")
    @TableField("config_key")
    private String configKey;

    @Schema(description = "值")
    @TableField("config_value")
    private String configValue;

    @Schema(description = "该配置属于哪个系统")
    @TableField("owner")
    private String owner;

    @Schema(description = "过期时间")
    @TableField("expire_timestamp")
    private Long expireTimestamp;

    public static SystemConfigDTO toDTO(SystemConfig entity) {
        if(entity == null) return null;
        SystemConfigDTO dto = new SystemConfigDTO();
        dto.setId(entity.getId());
        dto.setConfigKey(entity.getConfigKey());
        dto.setConfigValue(entity.getConfigValue());
        return dto;
    }

    public static SystemConfig toEntity(SystemConfigDTO dto) {
        if(dto == null) return null;
        SystemConfig entity = new SystemConfig();
        entity.setId(dto.getId());
        entity.setConfigKey(dto.getConfigKey());
        entity.setConfigValue(dto.getConfigValue());
        return entity;
    }

    public static List<SystemConfigDTO> toDTO(List<SystemConfig> list) {
        ArrayList<SystemConfigDTO> dtoList = new ArrayList<>();
        for (SystemConfig entity : list) {
            SystemConfigDTO dto = SystemConfig.toDTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static List<SystemConfig> toEntity(List<SystemConfigDTO> dtoList) {
        ArrayList<SystemConfig> list = new ArrayList<>();
        for (SystemConfigDTO dto : dtoList) {
            SystemConfig entity = SystemConfig.toEntity(dto);
            list.add(entity);
        }
        return list;
    }
}
