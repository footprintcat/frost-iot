package com.footprintcat.frostiot.common.internal;

import org.jetbrains.annotations.NotNull;

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

}
