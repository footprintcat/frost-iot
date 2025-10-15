/*
 * Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)
 *
 * This source code is licensed under the BSD-3-Clause license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.footprintcat.frostiot.core.api;

import com.footprintcat.frostiot.common.utils.StringUtils;
import com.footprintcat.frostiot.core.service.HelloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Hello 测试接口")
@Slf4j
@RestController
@RequestMapping
public class HelloController {

    @Resource
    private HelloService helloService;

    @PostConstruct
    @Operation(summary = "根目录接口",
        description = "用于判断后端是否已启动及网络是否联通。<br>请求成功会返回 “Hello World” 文字")
    @GetMapping
    public String hello() {
        return "Hello World";
    }

/*
    @Operation(summary = "测试接口，用于测试中文是否乱码")
    @GetMapping(path = "/hi", version = "1")
    public String hi() {
        // 测试中文是否乱码
        log.info("[GET] hi 你好呀");
        return "你好";
    }
*/

    @Operation(summary = "测试接口，用于测试中文是否乱码（第二版接口）")
    // @GetMapping(path = "/hi", version = "2")
    @GetMapping(path = "/hi")
    public String hiV2() {
        // 测试中文是否乱码
        log.info("[GET] hi 你好呀（第二版接口）");
        return "你好（第二版接口）";
    }

    @Operation(summary = "测试接口，用于判断传入参数是否为空")
    @GetMapping("/isEmpty")
    public boolean isEmpty(
        @Parameter(description = "传入字符串")
        @Nullable @RequestParam("str") String strVal
    ) {
        return StringUtils.isEmpty(strVal);
    }

    @Operation(summary = "测试接口，中文编码测试")
    @GetMapping("/encoding")
    public String testEncoding() {
        System.out.println("System.out: 中文测试");
        log.info("log.info: 中文测试");
        return "检查控制台输出";
    }

    @Operation(summary = "测试接口，Service IoC 注入测试",
        description = "")
    @GetMapping("/injectService")
    public String injectService() {
        return helloService.sayHello();
    }

    @Operation(summary = "测试接口，隐藏不在接口文档中显示的接口", hidden = true)
    @GetMapping("/hiddenApi")
    public String hiddenApi() {
        return "Aha! 被你发现了！";
    }

}
