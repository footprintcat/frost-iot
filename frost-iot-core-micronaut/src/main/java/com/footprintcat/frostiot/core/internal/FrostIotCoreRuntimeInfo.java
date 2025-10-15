/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.internal;

import com.footprintcat.frostiot.common.internal.IFrostIotRuntimeInfo;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.server.HttpServerConfiguration;
import jakarta.inject.Singleton;
import lombok.Getter;

import java.util.Set;

/**
 * 模块信息
 *
 * @since 2025-05-16
 */
@Getter
@Singleton
public class FrostIotCoreRuntimeInfo implements IFrostIotRuntimeInfo {

    public FrostIotCoreRuntimeInfo(ApplicationContext applicationContext,
                                   HttpServerConfiguration serverConfiguration) {
        Set<String> activeNames = applicationContext.getEnvironment().getActiveNames();
        appProfileList = activeNames.toArray(new String[0]);

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
     * 当前模块加载的配置文件
     *
     * @since 2025-05-17
     */
    private final String[] appProfileList;

    /**
     * 当前模块加载的配置文件说明
     *
     * @since 2025-05-17
     */
    @Value("${frost-iot.profile-name:<unset>}")
    private String appProfileName;

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

}
