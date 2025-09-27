/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.common.utils;

/**
 * @since 2024-08-03
 */
public class ConsoleUtils {

    // ANSI转义码常量
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        System.out.println("这是正常的文本");
        System.out.println(ANSI_BLACK + "这是黑色的文本" + ANSI_RESET);
        System.out.println(ANSI_RED + "这是红色的文本" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "这是绿色的文本" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "这是黄色的文本" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "这是蓝色的文本" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "这是紫色的文本" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "这是青色的文本" + ANSI_RESET);
        System.out.println(ANSI_WHITE + "这是白色的文本" + ANSI_RESET);
    }
}
