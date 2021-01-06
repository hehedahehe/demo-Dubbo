package com.hehedahehe.order;

import com.hehedahehe.order.api.IOrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0")
public class OrderServiceImpl implements IOrderService {

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    }
    )
    @Override
    public String sayHello(String word) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "DemoProviderServiceImpl ===" + word;
    }
}

