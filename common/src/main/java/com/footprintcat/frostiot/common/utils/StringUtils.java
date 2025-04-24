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
}
