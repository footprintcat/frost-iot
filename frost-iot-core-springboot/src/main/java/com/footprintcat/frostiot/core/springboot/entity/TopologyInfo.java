/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.footprintcat.frostiot.common.dto.TopologyInfoDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行时拓扑信息实体
 */
@Data
@TableName("topology_runtime_info")
public class TopologyInfo {

    @TableId
    private Long id;
    private String nodeId;
    private String direction;
    private Integer interval;
    private String targetNodeId;


    public static TopologyInfoDTO toDTO(TopologyInfo entity) {
        TopologyInfoDTO dto = new TopologyInfoDTO();
        dto.setId(entity.getId());
        dto.setNodeId(entity.getNodeId());
        dto.setDirection(TopologyInfoDTO.Direction.valueOf(entity.getDirection().toUpperCase()));
        dto.setInterval(entity.getInterval());
        dto.setTargetNodeId(entity.getTargetNodeId());
        return dto;
    }

    public static TopologyInfo toEntity(TopologyInfoDTO dto) {
        TopologyInfo entity = new TopologyInfo();
        entity.setId(dto.getId());
        entity.setNodeId(dto.getNodeId());
        entity.setDirection(dto.getDirection().toString().toLowerCase());
        entity.setInterval(dto.getInterval());
        entity.setTargetNodeId(dto.getTargetNodeId());
        return entity;
    }

    public static List<TopologyInfoDTO> toDTO(List<TopologyInfo> list) {
        ArrayList<TopologyInfoDTO> dtoList = new ArrayList<>();
        for (TopologyInfo entity : list) {
            TopologyInfoDTO dto = TopologyInfo.toDTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static List<TopologyInfo> toEntity(List<TopologyInfoDTO> dtoList) {
        ArrayList<TopologyInfo> list = new ArrayList<>();
        for (TopologyInfoDTO dto : dtoList) {
            TopologyInfo entity = TopologyInfo.toEntity(dto);
            list.add(entity);
        }
        return list;
    }
}
