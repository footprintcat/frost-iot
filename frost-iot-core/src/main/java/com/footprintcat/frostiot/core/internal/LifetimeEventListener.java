package com.footprintcat.frostiot.core.internal;

import com.footprintcat.frostiot.common.utils.SystemInfoUtils;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerShutdownEvent;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

/**
 * core 项目生命周期事件
 *
 * @since 2025-04-25
 */
@Singleton
@Slf4j
public class LifetimeEventListener {

    // @Inject docs: https://micronaut.bookhub.tech/action/service#ioc-%E6%B3%A8%E8%A7%A3
    @Inject
    FrostIotCoreModuleInfo frostIotCoreModuleInfo;

    @EventListener
    public void onStartup(ServerStartupEvent event) {

        // // 检查当前 charset
        // ConsoleUtils.checkCharsetIsUTF8();

        // 打印系统信息
        SystemInfoUtils.printSystemInfo(frostIotCoreModuleInfo);

        // 初始化逻辑
        log.info("Micronaut 应用已启动！");
    }

    @EventListener
    public void onShutdown(ServerShutdownEvent event) {
        log.info("应用正在停止...");
        // 执行清理逻辑（如关闭数据库连接、释放资源）
    }

}
