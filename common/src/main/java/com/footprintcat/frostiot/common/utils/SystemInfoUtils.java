package com.footprintcat.frostiot.common.utils;

import com.footprintcat.frostiot.common.internal.IFrostIotModuleInfo;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;

/**
 * 系统信息打印工具
 *
 * @since 2025-04-25
 */
public class SystemInfoUtils {

    /**
     * 打印软件信息、系统信息、系统状态及版权信息
     *
     * @since 2025-04-25
     */
    public static void printSystemInfo(@NotNull final IFrostIotModuleInfo moduleInfo) {

        // 2025.05.17 开源项目不应存在 All rights reserved 字样
        // Copyright © 2025 footprintcat. All rights reserved.
        // 武汉脚印猫科技有限公司
        // Wuhan Footprint Cat Technology Co., Ltd

        int year = Calendar.getInstance().get(Calendar.YEAR);
        System.out.print(ConsoleUtils.ANSI_YELLOW);
        System.out.println("┬─┐┌─┐┌─┐┌┬┐┌─┐┬─┐ ┬ ┌┐┌┌┬┐┌─┐┌─┐┌┬┐");
        System.out.println("├┤ │ ││ │ │ ├─┘├┬┘ │ │││ │ │  ├─┤ │ ");
        System.out.println("┴  └─┘└─┘ ┴ ┴  ┴└─ ┴ ┘└┘ ┴ └─┘┴ ┴ ┴ ");
        // System.out.println("Copyright (c) 2023 - " + year + " 武汉脚印猫科技有限公司 / Wuhan Footprint Cat Technology Co., Ltd.");
        // System.out.println("Copyright (c) 2023 - " + year + " Wuhan Footprint Cat Technology Co., Ltd. (武汉脚印猫科技有限公司)");
        System.out.println("Copyright (c) 2023 - " + year + " 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)");
        System.out.print(ConsoleUtils.ANSI_RESET);
        System.out.println();

        // System.out.println("[LICENSE]");
        System.out.println(moduleInfo.getAppLicenseSpdxIdentifier());
        System.out.println("Licensed under the " + moduleInfo.getAppLicense() + " License, see LICENSE for details.");
        System.out.println();

        System.out.println("[软件信息]");
        printInfoLine("软件名称   ", moduleInfo.getAppName());
        printInfoLine("模块      ", moduleInfo.getAppModule() + " " + moduleInfo.getAppVersion());
        System.out.println();

        // 当前时间
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("[配置信息]");
        printInfoLine("配置文件   ", "[" + String.join(", ", moduleInfo.getAppProfileList()) + "]");
        printInfoLine("配置文件说明", moduleInfo.getAppProfileName());
        printInfoLine("模块启动时间", currentTime);
        printInfoLine("服务 URL  ", moduleInfo.getRootUrlWithScheme());
        System.out.println();

        System.out.println("[技术栈]");
        printInfoLine("模块框架   ", moduleInfo.getFrameworkName() + " " + moduleInfo.getFrameworkVersion());
        System.out.println();

        // 获取系统属性
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        String osVersion = props.getProperty("os.version");
        String javaVersion = props.getProperty("java.version");
        String javaVendor = props.getProperty("java.vendor");
        String userTimezone = props.getProperty("user.timezone");
        // String appVersion = getClass().getPackage().getImplementationVersion(); // 从 MANIFEST.MF 获取版本

        System.out.println("[系统信息]");
        printInfoLine("Java 版本 ", javaVersion + " (" + javaVendor + ")");
        printInfoLine("操作系统   ", osName + " (os version:" + osVersion + ")");
        printInfoLine("时区      ", userTimezone);
        printInfoLine("字符集编码 ", System.out.charset().toString() + " (Default: " + Charset.defaultCharset().name() + ")");
        System.out.println();

        System.out.println("[系统状态]");
        Runtime runtime = Runtime.getRuntime();
        // CPU 核心
        int availableProcessors = runtime.availableProcessors();
        printInfoLine("CPU 核心数 ", String.valueOf(availableProcessors));

        // JVM 内存
        long maxMemory = runtime.maxMemory();
        long freeMemory = runtime.freeMemory();
        printInfoLine("JVM 内存  ", beautifyByteNumber(freeMemory) + " (可用) / " + beautifyByteNumber(maxMemory) + " (最大)");

        com.sun.management.OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 物理内存
        long totalMemorySize = osBean.getTotalMemorySize();
        long freeMemorySize = osBean.getFreeMemorySize();
        printInfoLine("系统内存   ", beautifyByteNumber(freeMemorySize) + " (可用) / " + beautifyByteNumber(totalMemorySize) + " (最大)");

        // Swap 空间
        long totalSwapSpaceSize = osBean.getTotalSwapSpaceSize();
        long freeSwapSpaceSize = osBean.getFreeSwapSpaceSize();
        printInfoLine("Swap 空间 ", beautifyByteNumber(freeSwapSpaceSize) + " (可用) / " + beautifyByteNumber(totalSwapSpaceSize) + " (最大)");

        System.out.println();
    }

    private static void printInfoLine(@NotNull String title, @NotNull String value) {
        System.out.println(ConsoleUtils.ANSI_PURPLE + title + ConsoleUtils.ANSI_RESET + "\t: " + value);
    }

    private static String beautifyByteNumber(long byteNumber) {
        long mb = byteNumber / 1024 / 1024; // MB
        String mbStr = String.valueOf(mb);
        // if (mb < 1024) {
        //     return StringUtils.leftPad(mbStr, 6, ' ') + " MB";
        // }
        double gb = mb / 1024.0;
        String gbStr = String.format("%.2f", gb);
        return StringUtils.leftPad(mbStr, 6, ' ') + " MB (" + StringUtils.leftPad(gbStr, 6, ' ') + " GB)";
    }
}
