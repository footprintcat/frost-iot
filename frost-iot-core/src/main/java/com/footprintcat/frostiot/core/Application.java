package com.footprintcat.frostiot.core;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Frost IoT 开放接口",
                version = "${frost-iot.api.version:0.0}",
                description = "Frost IoT Core 模块开放接口",
                license = @License(name = "BSD 3-Clause License", url = "https://opensource.org/license/BSD-3-clause"),
                contact = @Contact(url = "https://iot.footprintcat.com", name = "Frost IoT Project (Footprintcat Open Source)", email = "contact@footprintcat.com")
        )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}