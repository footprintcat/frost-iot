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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.footprintcat.frostiot.common.dto.master.TopologyInfoDTO;
import com.footprintcat.frostiot.common.repository.master.ITopologyInfoRepository;
import com.footprintcat.frostiot.common.utils.StringUtils;
import com.footprintcat.frostiot.core.springboot.entity.TopologyInfo;
import com.footprintcat.frostiot.core.springboot.mapper.master.TopologyInfoMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopologyInfoRepository extends ServiceImpl<TopologyInfoMapper, TopologyInfo> implements ITopologyInfoRepository {

    @Override
    public void saveOrUpdate(TopologyInfoDTO topologyInfoDTO) {
        TopologyInfo entity = TopologyInfo.toEntity(topologyInfoDTO);
        baseMapper.insertOrUpdate(entity);
    }

    @Override
    public boolean setConnectStatus(String nodeId, String targetNodeId, String status) {
        LambdaUpdateWrapper<TopologyInfo> wrapper = new LambdaUpdateWrapper<>();
        if(StringUtils.isEmpty(nodeId) && StringUtils.isEmpty(targetNodeId)){
            throw new RuntimeException("nodeId 和 targetNodeId 不能同时为空");
        }
        if(StringUtils.isNotEmpty(nodeId)){
            wrapper.eq(TopologyInfo::getNodeId, nodeId);
        }
        if(StringUtils.isNotEmpty(targetNodeId)){
            wrapper.eq(TopologyInfo::getTargetNodeId, targetNodeId);
        }
        wrapper.set(TopologyInfo::getStatus, status);

        int update = baseMapper.update(wrapper);

        return update > 0;
    }

    @Override
    public boolean delTopologyInfo(String nodeId, String targetNodeId) {
        int delete = baseMapper.delete(new LambdaQueryWrapper<TopologyInfo>()
            .eq(TopologyInfo::getNodeId, nodeId)
            .eq(TopologyInfo::getTargetNodeId, targetNodeId));
        return delete > 0;
    }

    @Override
    public TopologyInfoDTO getByNodeIdAndTargetNodeId(@NotNull String nodeId, @NotNull String targetNodeId) {
        TopologyInfo topologyInfo = baseMapper.selectOne(new LambdaQueryWrapper<TopologyInfo>()
            .eq(TopologyInfo::getNodeId, nodeId)
            .eq(TopologyInfo::getTargetNodeId, targetNodeId)
            .last("LIMIT 1"));
        return TopologyInfo.toDTO(topologyInfo);
    }

    @Override
    public List<TopologyInfoDTO> getSubOrSupNodes(@NotNull String nodeId, @NotNull String direction) {
        List<TopologyInfo> subNodes = baseMapper.selectList(new LambdaQueryWrapper<TopologyInfo>()
            .eq(TopologyInfo::getNodeId, nodeId)
            .eq(TopologyInfo::getRelation, direction));

        return TopologyInfo.toDTO(subNodes);
    }

    @Override
    public List<TopologyInfoDTO> getAllSubsOrSupsByNodeId(@NotNull String nodeId, @NotNull String direction) {
        List<TopologyInfo> nodes = baseMapper.selectAllSubsOrSupsByNodeId(nodeId, direction);
        return TopologyInfo.toDTO(nodes);
    }

    @Override
    public void saveBatchTopologyInfo(List<TopologyInfoDTO> topologyInfoDTOList) {
        List<TopologyInfo> entityList = TopologyInfo.toEntity(topologyInfoDTOList);
        // 事务失效
        saveBatch(entityList);
    }

    @Override
    public void clear() {
        baseMapper.delete(null);
    }
}
