/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.mapper.master;

import com.footprintcat.frostiot.core.springboot.pojo.test.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * @since 2025-10-22
 */
@Mapper
public interface MyXmlMapper {
    void insert(Message message);

    Message select(Integer id);
}
