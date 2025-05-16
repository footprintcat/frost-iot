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
