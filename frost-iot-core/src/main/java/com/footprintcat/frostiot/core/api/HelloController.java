package com.footprintcat.frostiot.core.api;

import com.footprintcat.frostiot.common.utils.StringUtils;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Hello 测试接口")
@Slf4j
@Controller
public class HelloController {

    @Operation(summary = "根目录接口，用于判断后端是否已启动及网络是否联通。请求成功会返回 “Hello World” 文字")
    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World";
    }

    @Operation(summary = "测试接口，用于测试中文是否乱码")
    @Get("/hi")
    public String hi() {
        // 测试中文是否乱码
        log.info("[GET] hi 你好呀");
        return "你好";
    }

    @Operation(summary = "测试接口，用于判断传入参数是否为空")
    @Get("/isEmpty")
    public boolean isEmpty(
            @Parameter(description = "传入字符串") @Nullable @QueryValue("str") String strVal
    ) {
        return StringUtils.isEmpty(strVal);
    }

    @Operation(summary = "测试接口，中文编码测试")
    @Get("/encoding")
    public String testEncoding() {
        System.out.println("System.out: 中文测试");
        log.info("log.info: 中文测试");
        return "检查控制台输出";
    }

    @Operation(summary = "测试接口，隐藏不在接口文档中显示的接口", hidden = true)
    @Get("/hiddenApi")
    public String hiddenApi() {
        return "Aha! 被你发现了！";
    }

}
