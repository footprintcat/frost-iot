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
