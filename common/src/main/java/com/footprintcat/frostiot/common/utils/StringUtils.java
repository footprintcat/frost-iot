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
 * 字符串工具类
 *
 * @since 2024-06-07
 */
public class StringUtils {

    public final static String EMPTY_STRING = "";

    public static boolean hasLength(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    public static String trimToNull(String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(String str, String defaultStr) {
        return str == null ? defaultStr : str.trim();
    }

    public static String trimToEmpty(String str, String defaultStr) {
        return str == null ? defaultStr : str.trim();
    }

    /**
     * 在字符串左侧填充指定字符，直到达到指定长度
     *
     * @param str     原始字符串，如果为null则视为空字符串
     * @param size    填充后的总长度
     * @param padChar 用于填充的字符
     * @return 填充后的字符串。如果原始字符串长度已经等于或大于size，则返回原始字符串
     * @since 2025-04-25
     */
    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            str = "";
        }

        int strLength = str.length();
        if (strLength >= size) {
            return str;
        }

        // StringBuilder sb = new StringBuilder(size);
        // for (int i = 0; i < size - strLength; i++) {
        //     sb.append(padChar);
        // }
        // sb.append(str);
        // return sb.toString();
        return String.valueOf(padChar).repeat(size - strLength) + str;
    }

    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    /**
     * 在字符串右侧填充指定字符，直到达到指定长度
     *
     * @param str     原始字符串，如果为null则视为空字符串
     * @param size    填充后的总长度
     * @param padChar 用于填充的字符
     * @return 填充后的字符串。如果原始字符串长度已经等于或大于size，则返回原始字符串
     * @since 2025-04-25
     */
    public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            str = "";
        }

        int strLength = str.length();
        if (strLength >= size) {
            return str;
        }

        // StringBuilder sb = new StringBuilder(size);
        // sb.append(str);
        // for (int i = strLength; i < size; i++) {
        //     sb.append(padChar);
        // }
        // return sb.toString();
        return str + String.valueOf(padChar).repeat(size - strLength);
    }

    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }
}
