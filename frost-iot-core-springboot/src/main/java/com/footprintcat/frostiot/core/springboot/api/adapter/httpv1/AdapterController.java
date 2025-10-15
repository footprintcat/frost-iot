/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.springboot.api.adapter.httpv1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备 http 接入接口
 *
 * @since 2025-07-07
 */
@Tag(name = "设备 http 接入接口")
@Slf4j
@RestController
@RequestMapping("/adapter/http/v1")
public class AdapterController {

    @Operation(summary = "握手前调用，用于获取 core 模块基础版本信息，判断是否能够建立连接")
    // @GetMapping(path = "/preHandshake", version = "1")
    @GetMapping(path = "/preHandshake")
    @ResponseBody
    public String preHandshake() {
        return "你好";
    }
}
