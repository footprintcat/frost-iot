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
import com.baomidou.mybatisplus.annotation.TableName;
import com.footprintcat.frostiot.common.dto.master.TopologyInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行时拓扑信息实体类
 */
@Data
@TableName("topology_runtime_info")
public class TopologyInfo {

    @Schema(description = "雪花ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "节点ID")
    @TableField("node_id")
    private String nodeId;

    @Schema(description = "方向（sub：下级；sup：上级）")
    @TableField("direction")
    private String direction;

    @Schema(description = "间隔节点数")
    @TableField("interval")
    private Integer interval;

    @Schema(description = "目标节点")
    @TableField("target_node_id")
    private String targetNodeId;

    @Schema(description = "是否连接（否为临时断连）")
    @TableField("is_connected")
    private Boolean isConnected;


    public static TopologyInfoDTO toDTO(TopologyInfo entity) {
        TopologyInfoDTO dto = new TopologyInfoDTO();
        dto.setId(entity.getId());
        dto.setNodeId(entity.getNodeId());
        dto.setDirection(TopologyInfoDTO.Direction.getByCode(entity.getDirection()));
        dto.setInterval(entity.getInterval());
        dto.setTargetNodeId(entity.getTargetNodeId());
        dto.setIsConnected(entity.getIsConnected());
        return dto;
    }

    public static TopologyInfo toEntity(TopologyInfoDTO dto) {
        TopologyInfo entity = new TopologyInfo();
        entity.setId(dto.getId());
        entity.setNodeId(dto.getNodeId());
        entity.setDirection(dto.getDirection().getCode());
        entity.setInterval(dto.getInterval());
        entity.setTargetNodeId(dto.getTargetNodeId());
        entity.setIsConnected(dto.getIsConnected());
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
