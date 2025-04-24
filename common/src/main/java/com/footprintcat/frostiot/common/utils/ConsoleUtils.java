package com.footprintcat.frostiot.common.utils;

import java.nio.charset.StandardCharsets;

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

    /**
     * 检查当前 charset 是否为 UTF-8
     *
     * @return boolean
     */
    @Deprecated
    public static boolean checkCharsetIsUTF8() {
        // 输出乱码时，可以观察下 charset 是否正确。UTF-8：正确，GBK：乱码
        // gradle 乱码问题可参考：https://blog.csdn.net/qq_39553871/article/details/136201398
        // Windows 系统下可尝试添加环境变量：GRADLE_OPTS=-Dfile.encoding=utf-8，并彻底重启 idea
        if (System.out.charset() != StandardCharsets.UTF_8) {
            // 这样判断是否乱码不严谨
            System.out.println();
            System.out.println(ConsoleUtils.ANSI_RED + "Note: You may have encountered a garbled code problem." + ConsoleUtils.ANSI_RESET);
            System.out.println("charset: " + System.out.charset());
            return false;
        }
        return true;
    }
}
