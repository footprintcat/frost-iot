package com.footprintcat.frostiot.common.utils;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * 系统信息打印工具
 *
 * @since 2025-04-25
 */
public class SystemInfoUtils {

    /**
     * 打印系统信息、版本、版权信息
     *
     * @since 2025-04-25
     */
    public static void printSystemInfo(String appVersion) {

        // 获取系统属性
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        String osVersion = props.getProperty("os.version");
        String javaVersion = props.getProperty("java.version");
        String javaVendor = props.getProperty("java.vendor");
        String userTimezone = props.getProperty("user.timezone");
        // String appVersion = getClass().getPackage().getImplementationVersion(); // 从 MANIFEST.MF 获取版本

        // 当前时间
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("[系统信息]");
        printInfoLine("应用版本   ", appVersion);
        printInfoLine("启动时间   ", currentTime);
        printInfoLine("Java 版本 ", javaVersion + " (" + javaVendor + ")");
        printInfoLine("操作系统   ", osName + " (os version:" + osVersion + ")");
        printInfoLine("时区      ", userTimezone);
        printInfoLine("字符集编码 ", System.out.charset().toString());
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

    private static void printInfoLine(String title, String value) {
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
