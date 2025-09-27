/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.adapter.sdk.enumeration;

/**
 * @since 2025-04-23
 */
public interface IEnumeration {

    /**
     * 获取枚举项英文编码
     */
    String getCode();

    /**
     * 获取枚举项中文名称
     */
    String getName();
}
