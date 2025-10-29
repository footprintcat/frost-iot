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
import com.footprintcat.frostiot.common.dto.TopologyInfoDTO;
import com.footprintcat.frostiot.common.repository.master.ITopologyInfoRepository;
import com.footprintcat.frostiot.core.springboot.entity.TopologyInfo;
import com.footprintcat.frostiot.core.springboot.mapper.master.TopologyInfoMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopologyInfoRepository extends ServiceImpl<TopologyInfoMapper, TopologyInfo> implements ITopologyInfoRepository {

    @Override
    public void setTopologyInfo(TopologyInfoDTO topologyInfoDTO) {
        TopologyInfo entity = TopologyInfo.toEntity(topologyInfoDTO);
        baseMapper.insert(entity);
    }

    @Override
    public TopologyInfoDTO getTopologyInfo(Long id) {
        TopologyInfo topologyInfo = baseMapper.selectById(id);
        return TopologyInfo.toDTO(topologyInfo);
    }

    @Override
    public boolean setTemporaryDisconnect(String nodeId, String targetNodeId, boolean isConnected) {
        return true;
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
            .eq(TopologyInfo::getDirection, direction));

        return TopologyInfo.toDTO(subNodes);
    }

    @Override
    public List<TopologyInfoDTO> getAllSubsOrSupsByNodeId(@NotNull String nodeId, @NotNull String direction) {
        List<TopologyInfo> nodes = baseMapper.selectAllSubsOrSupsByNodeId(nodeId, direction);
        return TopologyInfo.toDTO(nodes);
    }

    @Override
    public void clear() {
        baseMapper.delete(null);
    }
}
