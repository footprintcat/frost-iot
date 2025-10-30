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
import com.footprintcat.frostiot.common.enums.NodeTypeEnum;
import com.footprintcat.frostiot.common.enums.TopologyRelationEnum;
import com.footprintcat.frostiot.common.enums.TopologyStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行时拓扑信息实体类
 */
@Data
@Schema(name = "TopologyInfo", description = "运行时拓扑信息实体类")
@TableName("topology_runtime_info")
public class TopologyInfo {

    @Schema(description = "雪花ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "节点ID")
    @TableField("node_id")
    private String nodeId;

    @Schema(description = "节点类型")
    @TableField("node_type")
    private String nodeType;

    @Schema(description = "方向（sub：下级；sup：上级）")
    @TableField("relation")
    private String relation;

    @Schema(description = "间隔节点数")
    @TableField("interval")
    private Integer interval;

    @Schema(description = "目标节点")
    @TableField("target_node_id")
    private String targetNodeId;

    @Schema(description = "目标节点类型")
    @TableField("target_node_type")
    private String targetNodeType;

    @Schema(description = "是否连接")
    @TableField("status")
    private String status;

    public static TopologyInfoDTO toDTO(TopologyInfo entity) {
        TopologyInfoDTO dto = new TopologyInfoDTO();
        dto.setId(entity.getId());
        dto.setNodeId(entity.getNodeId());
        dto.setNodeType(NodeTypeEnum.getByCode(entity.getNodeType()));
        dto.setRelation(TopologyRelationEnum.getByCode(entity.getRelation()));
        dto.setInterval(entity.getInterval());
        dto.setTargetNodeId(entity.getTargetNodeId());
        dto.setTargetNodeType(NodeTypeEnum.getByCode(entity.getTargetNodeType()));
        dto.setStatus(TopologyStatusEnum.getByCode(entity.getStatus()));
        return dto;
    }

    public static TopologyInfo toEntity(TopologyInfoDTO dto) {
        TopologyInfo entity = new TopologyInfo();
        entity.setId(dto.getId());
        entity.setNodeId(dto.getNodeId());
        entity.setNodeType(dto.getNodeType().getCode());
        entity.setRelation(dto.getRelation().getCode());
        entity.setInterval(dto.getInterval());
        entity.setTargetNodeId(dto.getTargetNodeId());
        entity.setTargetNodeType(dto.getTargetNodeType().getCode());
        entity.setStatus(dto.getStatus().getCode());
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
