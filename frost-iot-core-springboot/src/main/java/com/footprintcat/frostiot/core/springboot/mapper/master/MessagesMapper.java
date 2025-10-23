/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.mapper.master;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.footprintcat.frostiot.core.springboot.entity.test.Messages;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author nieqiurong
 */
@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {
    void insertXml(Messages message);

}
