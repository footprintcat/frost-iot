/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.config;


import com.footprintcat.frostiot.core.springboot.FrostIotCoreApplication;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeSerialization;

/**
 * 批量注册 Lambda 表达式捕获的类到 GraalVM
 * <p>
 * source from: https://github.com/nieqiurong/mybatis-native-demo/blob/mybatis-plus/src/main/java/com/example/nativedemo/LambdaRegistrationFeature.java
 * <p>
 * lambda 表达式注入到graal中
 *
 * @author ztp
 * @since 2023/8/18 11:53
 */
public class LambdaRegistrationFeature implements Feature {

    @Override
    public void duringSetup(DuringSetupAccess access) {
        // TODO 这里需要将lambda表达式所使用的成员类都注册上来,具体情况视项目情况而定,一般扫描@Controller和@Service的会多点.
        RuntimeSerialization.registerLambdaCapturingClass(FrostIotCoreApplication.class);
    }

}
