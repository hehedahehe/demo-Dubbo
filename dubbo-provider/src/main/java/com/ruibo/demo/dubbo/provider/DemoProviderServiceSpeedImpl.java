package com.ruibo.demo.dubbo.provider;

import com.ruibo.demo.dubboproviderapi.IDemoProviderService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "2.0")
public class DemoProviderServiceSpeedImpl implements IDemoProviderService {
	@Override
	public String SayHello(String word) {
		return "DemoProviderServiceSpeedImpl ===" + word;
	}
}
