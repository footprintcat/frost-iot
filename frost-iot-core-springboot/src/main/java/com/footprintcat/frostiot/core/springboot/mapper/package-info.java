/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * mapper 接口
 * <p>
 * GraalVM 打包时，需要将 mapper 对应的 xml 添加进去
 * <p>
 * 因为 mapper interface: com.footprintcat.frostiot.core.springboot.mapper.master.FooBarMapper
 * 对应 resources 目录中:                                            mapper/master/FooBarMapper.xml
 * <p>
 * 所以在 MyBatisNativeConfiguration 中将 com/footprintcat/frostiot/core/springboot/ 包名直接替换掉了
 * <p>
 * 相关函数：
 * <p>
 * - {@link com.footprintcat.frostiot.core.springboot.config.MyBatisNativeConfiguration.MyBatisBeanFactoryInitializationAotProcessor#processAheadOfTime MyBatisBeanFactoryInitializationAotProcessor#processAheadOfTime}
 *
 * @since 2025-10-23
 */
package com.footprintcat.frostiot.core.springboot.mapper;
