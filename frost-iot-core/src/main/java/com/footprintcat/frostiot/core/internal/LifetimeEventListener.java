package com.footprintcat.frostiot.core.internal;

import com.footprintcat.frostiot.common.utils.SystemInfoUtils;
import io.micronaut.context.annotation.Value;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerShutdownEvent;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import io.micronaut.core.version.VersionUtils;

/**
 * core 项目生命周期事件
 *
 * @since 2025-04-25
 */
@Singleton
@Slf4j
public class LifetimeEventListener {

    @Value("${frost-iot.version:?}")
    String appVersion;

    /**
     * 当前模块名称
     */
    final String appModule = "frost-iot-core";

    /**
     * 框架名称
     */
    final String micronautName = "Micronaut";

    /**
     * 框架版本号
     */
    final String micronautVersion = VersionUtils.getMicronautVersion();

    @EventListener
    public void onStartup(ServerStartupEvent event) {

        // // 检查当前 charset
        // ConsoleUtils.checkCharsetIsUTF8();

        // 打印系统信息
        SystemInfoUtils.printSystemInfo(appModule, appVersion, micronautName, micronautVersion);

        // 初始化逻辑
        log.info("Micronaut 应用已启动！");
    }

    @EventListener
    public void onShutdown(ServerShutdownEvent event) {
        log.info("应用正在停止...");
        // 执行清理逻辑（如关闭数据库连接、释放资源）
    }

}
