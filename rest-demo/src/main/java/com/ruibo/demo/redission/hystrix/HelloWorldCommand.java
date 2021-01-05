package com.ruibo.demo.redission.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloWorldCommand extends HystrixCommand<String> {

	private final String name;

	public HelloWorldCommand(String name) {
		//最少配置:指定命令组名(CommandGroup)
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}


	@Override
	protected String run() throws Exception {
		// 依赖逻辑封装在run()方法中
		return "Hello " + name + " thread:" + Thread.currentThread().getName();
	}


	//调用实例
	public static void main(String[] args) throws Exception {

	}



	public static void testFallBack(){

	}
}

