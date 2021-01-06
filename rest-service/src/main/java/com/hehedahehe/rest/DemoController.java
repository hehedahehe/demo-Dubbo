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
    //参数配置 参照 https://blog.csdn.net/qq_32483795/article/details/108581724
    @Reference(
            version = "1.0",
            retries = 1,
            loadbalance = "roundrobin",
            timeout = 3000,
            parameters = {
                    "sayHello.retries", "1",
                    "sayHello.timeout", "1000"
            })
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
        }
        return "error";

    }

}
