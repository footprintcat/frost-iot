/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.internal;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * 模块信息 接口
 *
 * @since 2025-05-16
 */
public interface IFrostIotModuleInfo {

    /**
     * 软件名称
     *
     * @since 2025-05-17
     */
    @NotNull String getAppName();

    /**
     * 当前模块名称
     *
     * @since 2025-05-16
     */
    @NotNull String getAppModule();

    /**
     * 不带 scheme 的根 url
     *
     * @since 2025-05-17
     */
    @NotNull String getRootUrl();

    /**
     * 带 scheme 的根 url
     *
     * @since 2025-05-17
     */
    @NotNull String getRootUrlWithScheme();

    /**
     * 当前模块版本号
     *
     * @since 2025-05-16
     */
    @NotNull String getAppVersion();

    /**
     * 框架名称
     *
     * @since 2025-05-16
     */
    @NotNull String getFrameworkName();

    /**
     * 框架版本号
     *
     * @since 2025-05-16
     */
    @NotNull String getFrameworkVersion();

    /**
     * 当前模块加载的配置文件
     *
     * @since 2025-05-17
     */
    @NotNull Collection<String> getAppProfileList();

    /**
     * 当前模块加载的配置文件说明
     *
     * @since 2025-05-17
     */
    @NotNull String getAppProfileName();

    /**
     * 当前模块使用的 LICENSE 许可证名称 (结尾无需带 License 字样)
     *
     * @since 2025-05-17
     */
    @NotNull String getAppLicense();

    /**
     * 当前模块使用的 LICENSE 许可证 SPDX 标识符 (SPDX-License-Identifier)
     * <p>
     * <a href="https://spdx.dev/">SPDX website</a>
     *
     * @since 2025-05-17
     */
    @NotNull String getAppLicenseSpdxIdentifier();

}
