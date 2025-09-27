/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.utils;

import com.footprintcat.frostiot.common.internal.IFrostIotModuleInfo;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
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
    public static void printSystemInfo(@NotNull Charset consoleCharset, @NotNull final IFrostIotModuleInfo moduleInfo) {

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

        // 当前工作目录 Current Working Directory
        String userDir = System.getProperty("user.dir");

        // 调试模式
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        boolean isDebug = runtimeMXBean.getInputArguments().toString().contains("-agentlib:jdwp");

        System.out.println("[配置信息]");
        printInfoLine("配置文件   ", "[" + String.join(", ", moduleInfo.getAppProfileList()) + "]");
        printInfoLine("配置文件说明", moduleInfo.getAppProfileName());
        printInfoLine("模块启动时间", currentTime);
        printInfoLine("服务 URL  ", moduleInfo.getRootUrlWithScheme());
        printInfoLine("当前工作目录", userDir);
        printInfoLine("调试模式   ", isDebug ? "是" : "否");
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

        // JAVA_HOME
        String javaHome = System.getProperty("java.home");

        System.out.println("[系统信息]");
        printInfoLine("JDK 版本  ", javaVersion + " (" + javaVendor + ")");
        printInfoLine("操作系统   ", osName + " (os version:" + osVersion + ")");
        printInfoLine("时区      ", userTimezone);
        printInfoLine("字符集编码 ", consoleCharset + " (Default: " + Charset.defaultCharset().name() + ")");
        printInfoLine("JAVA_HOME ", javaHome);
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

    /**
     * 打印网卡信息
     *
     * @since 2025-05-17
     */
    public static void printNetworkInfo() {
        System.out.println("[网络信息] (此处仅展示私有地址)");
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            boolean isFirst = true;
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                // if (
                //     // 回环地址
                //         networkInterface.isLoopback()
                //                 // 未启用的网卡
                //                 || !networkInterface.isUp()
                //                 // 虚拟网卡
                //                 || networkInterface.isVirtual()
                //                 // 没有硬件地址
                //                 || networkInterface.getHardwareAddress() == null
                //                 // 排除Hyper-V、Docker等虚拟接口
                //                 || networkInterface.getDisplayName().matches(".*(Hyper-V|Virtual|Filter|Docker|VPN).*")
                // ) {
                //     continue;
                // }

                // 网卡接口下的ip会有多个，也需要遍历，找到自己所需要的
                Enumeration<InetAddress> addressesEnumeration = networkInterface.getInetAddresses();
                // ArrayList<InetAddress> addresses = Collections.list(addressesEnumeration);
                ArrayList<InetAddress> addresses = new ArrayList<>();
                while (addressesEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = addressesEnumeration.nextElement();
                    if (inetAddress.isLoopbackAddress()) {
                        // 跳过本地回环地址
                        // 网卡名称：loopback_0
                        // 展示名称：Software Loopback Interface 1
                        // isLoopbackAddress: true
                        // isLinkLocalAddress: false
                        // isSiteLocalAddress: false
                        // IP地址：0:0:0:0:0:0:0:1
                        // isLoopbackAddress: true
                        // isLinkLocalAddress: false
                        // isSiteLocalAddress: false
                        // IP地址：127.0.0.1
                        continue;
                    }
                    if (!inetAddress.isSiteLocalAddress()) {
                        // 跳过非私有地址
                        continue;
                    }
                    addresses.add(inetAddress);
                }

                if (addresses.isEmpty()) {
                    continue;
                }

                if (!isFirst) {
                    System.out.println("=============================================");
                } else {
                    isFirst = false;
                }

                // 获取网卡信息
                String name = networkInterface.getName(); // 网卡名称
                String displayName = networkInterface.getDisplayName(); // 网卡展示名称
                byte[] mac = networkInterface.getHardwareAddress(); // 网卡硬件地址

                // 打印网卡信息
                System.out.println("网卡名称：" + name);
                System.out.println("展示名称：" + displayName);
                if (mac != null) {
                    String macStr = printMacAddress(mac);
                    System.out.println("硬件 Mac 地址: " + macStr);
                }

                System.out.println("IP地址：");
                for (InetAddress inetAddress : addresses) {
                    // NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
                    // System.out.println("网卡名称：" + network.getName());
                    // System.out.println("展示名称：" + network.getDisplayName());
                    // byte[] hardwareAddress = network.getHardwareAddress();
                    // if (hardwareAddress != null) {
                    //     String macStr = printMacAddress(hardwareAddress);
                    //     System.out.println("硬件 Mac 地址: " + macStr);
                    // }
                    System.out.println("  -> " + inetAddress.getHostAddress());
                    // System.out.println("主机名：" + inetAddress.getHostName());
                    System.out.println("     " +
                            // 回环地址：用于本地主机内部通信的特殊地址，数据包不经过物理网络，直接由操作系统处理
                            // 通信范围：仅限本机（无法被外部访问）
                            // 典型场景：测试本地服务（如Web服务器、数据库）、诊断网络协议栈
                            // IPv4范围：127.0.0.0 ~ 127.255.255.255（即127.0.0.0/8），最常用的是127.0.0.1
                            // IPv6范围：::1（简写形式）
                            // 域名解析：localhost默认指向127.0.0.1（IPv4）或::1（IPv6）
                            // 特点：无需物理网络连接，安全性高（服务绑定到回环地址时，外部无法访问）
                            "Loopback: " + inetAddress.isLoopbackAddress() + "; " +

                            // 本地链路地址：这类地址用于同一物理网络内的设备间自动通信，无需手动配置或依赖DHCP服务器
                            // 通信范围：同一物理网络（如Wi-Fi）
                            // 典型场景：设备自动配置、临时通信
                            // IPv4范围：169.254.0.0 ~ 169.254.255.255（即169.254.0.0/16）
                            // IPv6范围：前缀为FE80::/10（如FE80::1）
                            // 当设备（如电脑、打印机）通过DHCP获取IP失败时，会自动分配此类地址（如169.254.1.1），确保局域网内设备能临时通信
                            // 仅在同一物理网络（如一个Wi-Fi网络）内有效，无法跨路由器通信
                            // 常用于设备初始配置或故障恢复（如打印机找不到DHCP时）
                            "LinkLocal: " + inetAddress.isLinkLocalAddress() + "; " +

                            // 私有地址：这类地址用于组织内部网络，不可在公网路由
                            // 通信范围：整个私有网络（跨路由器）
                            // 典型场景：家庭/企业内网服务
                            // IPv4范围：
                            //   10.0.0.0 ~ 10.255.255.255（10.0.0.0/8）
                            //   172.16.0.0 ~ 172.31.255.255（172.16.0.0/12）
                            //   192.168.0.0 ~ 192.168.255.255（192.168.0.0/16）
                            // IPv6范围：前缀为FEC0::/10（已废弃，现推荐使用FC00::/7的唯一本地地址ULA）
                            // 用途：家庭路由器分配的地址（如192.168.1.1）；企业内部服务器、数据库等私有服务
                            // 特点：需通过NAT（网络地址转换）才能访问公网。避免与公网IP冲突，增强安全性
                            "SiteLocal: " + inetAddress.isSiteLocalAddress()
                    );
                }
            }
        } catch (SocketException ignored) {
        }
        System.out.println();
    }

    /**
     * 格式化 mac 地址
     *
     * @param mac byte[] 类型的 mac 地址
     * @return 格式化后的 mac 地址字符串
     * @since 2025-05-17
     */
    private static String printMacAddress(byte[] mac) {
        StringBuilder macs = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            // 格式化十六进制
            macs.append(String.format("%02X", mac[i]));
            if (i < mac.length - 1) {
                macs.append("-");
            }
        }
        return macs.toString();
    }
}
