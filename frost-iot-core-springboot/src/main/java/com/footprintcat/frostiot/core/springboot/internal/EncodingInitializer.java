/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.internal;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * 解决 springboot log.info 打印编码问题
 * <p>
 * 问题描述：
 * Windows 系统下，控制台默认编码 System.out.charset(): x-mswin-936
 * 此时，通过系统命令行运行，System.out.log 编码正常，但 log.info 会出现中文乱码问题
 * 经过测试，可以通过
 * <p>
 * 注意：System.out.charset() 为 JDK 21 版本引入 api, JDK 17 不包含此 api
 *
 * @since 2025-05-28
 */
public class EncodingInitializer {

    /**
     * 解决 log.info 在 Windows 下，使用系统自带命令行(默认编码:x-mswin-936)时，打印中文出现乱码问题
     *
     * @return charset
     * @since 2025-05-28
     */
    public static @NotNull Charset init() {
        Charset consoleCharset = getConsoleCharset();

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);

        // 获取控制台 appender（可能需要遍历）
        ConsoleAppender<?> consoleAppender = (ConsoleAppender<?>) rootLogger.iteratorForAppenders()
            .next();

        if (consoleAppender != null) {
            PatternLayoutEncoder encoder = (PatternLayoutEncoder) consoleAppender.getEncoder();
            encoder.setCharset(consoleCharset);
            encoder.start();
        }

        return consoleCharset;
    }

    /**
     * 获取当前控制台 charset
     *
     * @return charset
     * @since 2025-05-28
     */
    public static @NotNull Charset getConsoleCharset() {
        Charset charset;
        try {
            // JDK21及以上版本：可通过 System.out.charset() 获取控制台编码。为支持低版本 JDK 编译通过，这里通过反射进行调用
            // 尝试获取 System.out的 charset() 方法
            Method charsetMethod = PrintStream.class.getMethod("charset");
            charset = (Charset) charsetMethod.invoke(System.out);
            // System.out.println("System.out.charset(): " + charset);
        } catch (NoSuchMethodException e) {
            // JDK17及以下
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out);
            String encoding = outputStreamWriter.getEncoding();
            charset = Charset.forName(encoding); // consoleCharset
            // System.out.println("Console charset: " + charset); // 例如 x-mswin-936
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return charset;
    }
}
