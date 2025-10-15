/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.internal;

import com.footprintcat.frostiot.common.utils.ConsoleUtils;
import com.footprintcat.frostiot.common.utils.SystemInfoUtils;
import com.footprintcat.frostiot.core.FrostIotCoreApplication;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * core 项目生命周期事件
 *
 * @since 2025-04-25
 */
@Component
@Slf4j
public class LifetimeEventListener implements ApplicationListener<@NotNull ApplicationReadyEvent> {

    private final FrostIotCoreRuntimeInfo runtimeInfo;

    private final FrostIotCoreModuleInfo moduleInfo = FrostIotCoreModuleInfo.getInstance();

    @NotNull
    private final Charset charset;
    private final boolean isUtf8Charset;

    public LifetimeEventListener(FrostIotCoreRuntimeInfo runtimeInfo) {
        this.runtimeInfo = runtimeInfo;
        // 解决 log.info 在 Windows 系统自带命令行打印时中文乱码问题
        charset = EncodingInitializer.init();
        isUtf8Charset = StandardCharsets.UTF_8.equals(charset);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        // 打印系统信息
        SystemInfoUtils.printSystemInfo(moduleInfo, runtimeInfo, FrostIotCoreApplication.class);

        // 打印网卡信息
        SystemInfoUtils.printNetworkInfo();

        // 打印接口文档地址
        Map<String, String> apiDocUrlMap = Map.of(
            "swagger-ui", "/swagger-ui/index.html",
            "redoc", "/redoc",
            "openapi-explorer", "/openapi-explorer",
            "scalar", "/scalar",
            "rapidoc", "/rapidoc"
        );
        // 找出最长的键名长度
        int maxKeyLength = apiDocUrlMap.keySet().stream()
            .mapToInt(String::length)
            .max()
            .orElse(0);
        System.out.println("[接口文档]");
        // apiDocUrlMap.forEach((k, v) ->
        //         System.out.println(String.format("%-" + maxKeyLength + "s", k) + " : " + frostIotCoreModuleInfo.getRootUrlWithScheme() + v)
        // );
        apiDocUrlMap.forEach((k, v) -> System.out.printf("%-" + (maxKeyLength + 1) + "s: %s%n",
            ConsoleUtils.ANSI_PURPLE + k + ConsoleUtils.ANSI_RESET, runtimeInfo.getRootUrlWithScheme() + v));
        System.out.println();

        // 初始化逻辑
        // log.info("Spring Boot 应用已启动！");

        // 2025.09.28 fix: 对于非 UTF-8 字符集的控制台可能不支持打印特殊字符（比如 GBK），这里做一下做兜底处理
        final String emoji = isUtf8Charset
            ? "(๑•̀ㅂ•́)و✧" // (ง •ᴗ•)ง
            : "(￣▽￣)ノ";
        log.info("应用启动成功 {}", emoji);
    }

    @PreDestroy
    public void onShutdown() {
        log.info("应用正在停止...");
        // 执行清理逻辑（如关闭数据库连接、释放资源）
    }

}
