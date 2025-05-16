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
     * 当前模块使用的 LICENSE
     *
     * @since 2025-05-17
     */
    @NotNull String getAppLicense();

}
