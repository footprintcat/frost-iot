/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.internal;

import com.footprintcat.frostiot.common.utils.SystemInfoUtils;
import io.micronaut.context.annotation.Context;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerShutdownEvent;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * core 项目生命周期事件
 *
 * @since 2025-04-25
 */
@Context // 此注解确保该 Bean 在上下文启动早期初始化
@Singleton
@Slf4j
public class LifetimeEventListener {

    // @Inject docs: https://micronaut.bookhub.tech/action/service#ioc-%E6%B3%A8%E8%A7%A3
    @Inject
    private FrostIotCoreRuntimeInfo runtimeInfo;

    FrostIotCoreModuleInfo moduleInfo = FrostIotCoreModuleInfo.getInstance();

    @NotNull
    private final Charset charset;

    LifetimeEventListener() {
        // 解决 log.info 在 Windows 系统自带命令行打印时中文乱码问题
        charset = EncodingInitializer.init();

        // 打印版权信息
        SystemInfoUtils.printCopyright(moduleInfo);
    }

    @EventListener
    public void onStartup(ServerStartupEvent event) {

        // 打印系统信息
        SystemInfoUtils.printSystemInfo(charset, moduleInfo, runtimeInfo);

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
                k, runtimeInfo.getRootUrlWithScheme() + v));
        System.out.println();

        // 初始化逻辑
        // log.info("Micronaut 应用已启动！");
        log.info("应用启动成功 (๑•̀ㅂ•́)و✧");
    }

    @EventListener
    public void onShutdown(ServerShutdownEvent event) {
        log.info("应用正在停止...");
        // 执行清理逻辑（如关闭数据库连接、释放资源）
    }

}
