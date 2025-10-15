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
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 模块信息
 *
 * @since 2025-05-16
 */
@Getter
@Component
public class FrostIotCoreRuntimeInfo implements IFrostIotRuntimeInfo {

    public FrostIotCoreRuntimeInfo(ApplicationContext applicationContext) {
        Environment env = applicationContext.getEnvironment();
        this.appProfileList = env.getActiveProfiles();

        String host = "localhost"; // 默认值
        int port = env.getProperty("server.port", Integer.class, -1); // 80

        this.rootUrl = host + ":" + port;
        this.rootUrlWithScheme = "http" + "://" + rootUrl;
        // 注: http:// 分两段拼接避免 idea 提示 [HTTP 链接不安全]
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
