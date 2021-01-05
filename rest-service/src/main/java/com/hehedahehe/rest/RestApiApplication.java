package com.hehedahehe.rest;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableDubbo
@SpringBootApplication
public class RestApiApplication {

    public static volatile boolean run = false;

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }


}
