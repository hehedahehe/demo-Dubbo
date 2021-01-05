package com.hehedahehe.rest;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableDubbo
@SpringBootApplication
public class DemoRestApplication {

    public static volatile boolean run = false;

    public static void main(String[] args) {
        SpringApplication.run(DemoRestApplication.class, args);
    }


}
