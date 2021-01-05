package com.ruibo.demo.redission.hystrix;

import com.netflix.hystrix.*;

import java.util.concurrent.TimeUnit;

public class HelloWorldCommandWithFailBack extends HystrixCommand<String> {
	private final String name;

	public HelloWorldCommandWithFailBack(String name) {
//		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
//				/* 配置依赖超时时间,500毫秒*/
//				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds(500)));
		/**
		 *  NOTE: 每个CommandKey代表一个依赖抽象,相同的依赖要使用相同的CommandKey名称。依赖隔离的根本就是对相同CommandKey的依赖做隔离.
		 */
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
				                 /* HystrixCommandKey工厂定义依赖名称 */
				                 .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld")));
		/**
		 * 命令分组用于对依赖操作分组,便于统计,汇总等.
		 *  NOTE: CommandGroup是每个命令最少配置的必选参数，在不指定ThreadPoolKey的情况下，字面值用于对不同依赖的线程池/信号区分.
		 */
		Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"));

		/**
		 * 线程池/信号:ThreadPoolKey
		 *  NOTE: 当对同一业务依赖做隔离时使用CommandGroup做区分,但是对同一依赖的不同远程调用如(一个是redis 一个是http),可以使用HystrixThreadPoolKey做隔离区分.
		 *             最然在业务上都是相同的组，但是需要在资源上做隔离时，可以使用HystrixThreadPoolKey区分.
		 */
//		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
//				                 .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
//				                 /* 使用HystrixThreadPoolKey工厂定义线程池名称*/
//				                 .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool")));




		this.name = name;
	}

	/**
	 * NOTE: 除了HystrixBadRequestException异常之外，所有从run()方法抛出的异常都算作失败，并触发降级getFallback()和断路器逻辑。
	 * @return
	 */
	@Override
	protected String getFallback() {
		return "exeucute Falled";
	}

	@Override
	protected String run() throws Exception {
		//sleep 1 秒,调用会超时
		TimeUnit.MILLISECONDS.sleep(1000);
		return "Hello " + name + " thread:" + Thread.currentThread().getName();
	}

	public static void main(String[] args) throws Exception {
		HelloWorldCommand command = new HelloWorldCommand("test-Fallback");
		String result = command.execute();
	}
}
 /* 运行结果:getFallback() 调用运行
 getFallback executed
 */

