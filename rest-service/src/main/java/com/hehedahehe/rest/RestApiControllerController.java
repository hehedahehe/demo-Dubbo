package com.hehedahehe.rest;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class RestApiControllerController {

    Logger logger = LoggerFactory.getLogger(RestApiControllerController.class);

    @Resource
    private OrderService orderService;

    @GetMapping("/sayHi")
    public Object getHelloResponse(String name) {
        try {
            String resFromProvider = orderService.doSayHello(name);
            return ImmutableMap.of(
                    "orderService", resFromProvider
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";

    }

}
