package com.footprintcat.frostiot.core.internal;

import com.footprintcat.frostiot.common.internal.IFrostIotModuleInfo;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.version.VersionUtils;
import io.micronaut.http.server.HttpServerConfiguration;
import jakarta.inject.Singleton;
import lombok.Getter;

import java.util.Collection;

/**
 * 模块信息
 *
 * @since 2025-05-16
 */
@Getter
@Singleton
public class FrostIotCoreModuleInfo implements IFrostIotModuleInfo {

    public FrostIotCoreModuleInfo(ApplicationContext applicationContext,
                                  HttpServerConfiguration serverConfiguration) {
        appProfileList = applicationContext.getEnvironment().getActiveNames();

        rootUrl = serverConfiguration.getHost().orElse("localhost") + ":"
                + serverConfiguration.getPort().orElse(80);
        rootUrlWithScheme = "http://" + rootUrl;
    }

    /**
     * 当前模块版本号
     *
     * @since 2025-05-16
     */
    @Value("${frost-iot.version:?}")
    private String appVersion;

    /**
     * 软件名称
     *
     * @since 2025-05-17
     */
    private final String appName = "寒霜物联 core 模块";

    /**
     * 当前模块名称
     *
     * @since 2025-05-16
     */
    private final String appModule = "frost-iot-core";

    /**
     * 不带 scheme 的根 url
     *
     * @since 2025-05-17
     */
    private final String rootUrl;

    /**
     * 带 scheme 的根 url
     *
     * @since 2025-05-17
     */
    private final String rootUrlWithScheme;

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

    /**
     * 当前模块加载的配置文件
     *
     * @since 2025-05-17
     */
    private final Collection<String> appProfileList;

    /**
     * 当前模块加载的配置文件说明
     *
     * @since 2025-05-17
     */
    @Value("${frost-iot.profile-name:<unset>}")
    private String appProfileName;

    /**
     * 当前模块使用的 LICENSE 许可证名称 (结尾无需带 License 字样)
     *
     * @since 2025-05-17
     */
    private final String appLicense = "BSD 3-Clause";

    /**
     * 当前模块使用的 LICENSE 许可证 SPDX 标识符 (SPDX-License-Identifier)
     *
     * @since 2025-05-17
     */
    private final String appLicenseSpdxIdentifier = "SPDX-License-Identifier: BSD-3-Clause";

}
