/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.adapter.sdk.spi;

/**
 * @since 2025-04-23
 */
public interface IAdapter {

    /**
     * 获取 Adapter 名称
     * <p>
     * 注意：该名称需要唯一，且不可改变
     * <p>
     * <code>
     * &#064;Getter <br> private final String name = "FooBarAdapter";
     * </code>
     *
     * @return Adapter 名称
     * @since 2025-04-23
     */
    String getName();

    /**
     * 获取 Adapter 版本号
     * <p>
     * 注意：该名称需要唯一，且不可改变
     * <p>
     * <code>
     * &#064;Getter <br> private final int versionCode = 1;
     * </code>
     *
     * @return Adapter 版本号
     * @since 2025-04-23
     */
    int getVersionCode();

}
