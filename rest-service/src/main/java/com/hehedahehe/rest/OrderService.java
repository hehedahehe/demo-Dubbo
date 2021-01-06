package com.hehedahehe.rest;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

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
    private com.hehedahehe.order.api.IOrderService orderService;

    //调用失败后 实际进行reliable方法调用
    @HystrixCommand(fallbackMethod = "reliable")
    public String doSayHello(String name) {
        return orderService.sayHello(name);
    }

    public String reliable(String name) {
        return "hystrix fallback value";
    }
}
