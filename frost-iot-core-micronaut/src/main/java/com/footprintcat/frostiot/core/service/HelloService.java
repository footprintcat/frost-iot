/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.service;

import jakarta.inject.Singleton;

/**
 * @since 2025-05-17
 */
@Singleton
public class HelloService {

    public HelloService() {

    }

    public String sayHello() {
        return "Hello from service!";
    }
}
