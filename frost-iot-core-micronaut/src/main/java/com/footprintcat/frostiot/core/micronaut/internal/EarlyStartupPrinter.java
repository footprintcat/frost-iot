/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.micronaut.internal;

import com.footprintcat.frostiot.common.utils.SystemInfoUtils;
import io.micronaut.context.annotation.Context;
import lombok.extern.slf4j.Slf4j;

/**
 * banner.txt 打印后，hikari 日志打印前的时机
 *
 * @since 2025-10-15
 */
@Context
@Slf4j
public class EarlyStartupPrinter {

    public EarlyStartupPrinter() {
        // 打印版权信息
        FrostIotCoreModuleInfo moduleInfo = FrostIotCoreModuleInfo.getInstance();
        SystemInfoUtils.printCopyright(moduleInfo);
    }

}
