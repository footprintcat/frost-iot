package com.footprintcat.frostiot.core.internal;

import com.footprintcat.frostiot.common.internal.IFrostIotModuleInfo;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.version.VersionUtils;
import jakarta.inject.Singleton;
import lombok.Getter;

/**
 * 模块信息
 *
 * @since 2025-05-16
 */
@Getter
@Singleton
public class FrostIotCoreModuleInfo implements IFrostIotModuleInfo {

    /**
     * 当前模块版本号
     *
     * @since 2025-05-16
     */
    @Value("${frost-iot.version:?}")
    private String appVersion;

    /**
     * 当前模块名称
     *
     * @since 2025-05-16
     */
    private final String appModule = "frost-iot-core";

    /**
     * 框架名称
     *
     * @since 2025-05-16
     */
    private final String micronautName = "Micronaut";
    private final String frameworkName = micronautName;

    /**
     * 框架版本号
     *
     * @since 2025-05-16
     */
    private final String micronautVersion = VersionUtils.getMicronautVersion();
    private final String frameworkVersion = micronautVersion;

}
