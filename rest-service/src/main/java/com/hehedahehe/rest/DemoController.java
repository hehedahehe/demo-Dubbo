package com.hehedahehe.rest;

import com.google.common.collect.ImmutableMap;
import com.hehedahehe.order.api.IOrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Reference(version = "1.0", retries = 0)
    private IOrderService orderService;


    @GetMapping("/sayHi")
    public Object getHelloResponse(String name) {
        try {

            String resFromProvider = orderService.sayHello(name);
            return ImmutableMap.of(
                    "orderService", resFromProvider
            );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return "error";

    }

}
