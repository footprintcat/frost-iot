/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.internal;

import com.footprintcat.frostiot.common.utils.SystemInfoUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.bootstrap.ConfigurableBootstrapContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * banner.txt 打印后，hikari 日志打印前的时机
 * <p>
 * 需要在 resources/META-INF/spring.factories 中配置如下行
 * org.springframework.boot.SpringApplicationRunListener=com.footprintcat.frostiot.core.internal.EarlyStartupPrinter
 *
 * @since 2025-10-15
 */
public class EarlyStartupPrinter implements SpringApplicationRunListener {

    public EarlyStartupPrinter() {
        // System.out.println("EarlyStartupPrinter construct");
    }

    @Override
    public void starting(@NotNull ConfigurableBootstrapContext bootstrapContext) {
        // System.out.println("Starting Early Startup Printer");
    }

    @Override
    public void environmentPrepared(@NotNull ConfigurableBootstrapContext bootstrapContext, @NotNull ConfigurableEnvironment environment) {
        // System.out.println("Early Startup Printer Started");
    }

    @Override
    public void contextPrepared(@NotNull ConfigurableApplicationContext context) {
        // System.out.println("Early Startup Printer Context Prepared");
        // 打印版权信息
        FrostIotCoreModuleInfo moduleInfo = FrostIotCoreModuleInfo.getInstance();
        SystemInfoUtils.printCopyright(moduleInfo);
    }

    @Override
    public void contextLoaded(@NotNull ConfigurableApplicationContext context) {
        // System.out.println("Early Startup Printer Context Loaded");
    }

}
