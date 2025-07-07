package com.footprintcat.frostiot.core.api.adapter.httpv1;

import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备 http 接入接口
 *
 * @since 2025-07-07
 */
@Tag(name = "设备 http 接入接口")
@Slf4j
@Controller
public class AdapterController {

    @Operation(summary = "握手前调用，用于获取 core 模块基础版本信息，判断是否能够建立连接")
    @Version("1")
    @Get("/preHandshake")
    public String preHandshake() {
        return "你好";
    }
}
