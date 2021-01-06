package com.hehedahehe.order;

import com.hehedahehe.order.api.IOrderService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0")
public class OrderServiceImpl implements IOrderService {

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

