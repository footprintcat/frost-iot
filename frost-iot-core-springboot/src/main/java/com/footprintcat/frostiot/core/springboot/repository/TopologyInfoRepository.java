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
import com.footprintcat.frostiot.common.dto.TopologyInfoDTO;
import com.footprintcat.frostiot.common.repository.master.ITopologyInfoRepository;
import com.footprintcat.frostiot.core.springboot.entity.TopologyInfo;
import com.footprintcat.frostiot.core.springboot.mapper.master.TopologyInfoMapper;
import org.springframework.stereotype.Service;

@Service
public class TopologyInfoRepository extends ServiceImpl<TopologyInfoMapper, TopologyInfo> implements ITopologyInfoRepository {

    @Override
    public void save(TopologyInfoDTO topologyInfoDTO) {
        TopologyInfo entity = TopologyInfo.toEntity(topologyInfoDTO);
        baseMapper.insert(entity);
    }
}
