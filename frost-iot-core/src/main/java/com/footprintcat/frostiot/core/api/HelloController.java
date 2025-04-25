package com.footprintcat.frostiot.core.api;

import com.footprintcat.frostiot.common.utils.StringUtils;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HelloController {

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World";
    }

    @Get("/hi")
    public String hi() {
        // 测试中文是否乱码
        log.info("[GET] hi 你好呀");
        return "你好";
    }

    @Get("/isEmpty")
    public boolean isEmpty(@Nullable @QueryValue("str") String strVal) {
        return StringUtils.isEmpty(strVal);
    }

    @Get("/encoding")
    public String testEncoding() {
        System.out.println("System.out: 中文测试");
        log.info("log.info: 中文测试");
        return "检查控制台输出";
    }

}
