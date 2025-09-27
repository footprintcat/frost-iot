/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.internal;

import com.footprintcat.frostiot.common.internal.IFrostIotModuleInfo;
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

    private static FrostIotCoreModuleInfo frostIotCoreModuleInfo;

    private FrostIotCoreModuleInfo() {
    }

    public static FrostIotCoreModuleInfo getInstance() {
        if (frostIotCoreModuleInfo == null) {
            frostIotCoreModuleInfo = new FrostIotCoreModuleInfo();
        }
        return frostIotCoreModuleInfo;
    }

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
