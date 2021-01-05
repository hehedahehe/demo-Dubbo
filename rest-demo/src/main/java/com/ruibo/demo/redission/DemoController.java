package com.ruibo.demo.redission;

import com.google.common.collect.ImmutableMap;
import com.ruibo.demo.dubboproviderapi.IDemoProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;


@RestController
public class DemoController {

	Logger logger = LoggerFactory.getLogger(DemoController.class);

//	@Reference(version = "1.0", retries = 0)
	private IDemoProviderService providerService;
//	@Reference(version = "2.0", retries = 0)
	private IDemoProviderService providerServiceSpeed;
//	@Reference
//	private GreetingService greetingService;


	/**
	 * 2020-04-17 12:34:31.047 ERROR 54297 --- [nio-8001-exec-1] com.ruibo.demo.rest.DemoController       : Failed to invoke the method SayHello
	 * in the service com.ruibo.demo.dubboproviderapi.IDemoProviderService. Tried 3 times of the providers
	 * [192.168.0.103:20881] (1/1) from the registry localhost:2181 on the consumer 192.168.0.103 using the dubbo version 2.7.6. Last error is:
	 * Invoke remote method timeout. method: SayHello, provider:
	 * dubbo://192.168.0.103:20881/com.ruibo.demo.dubboproviderapi.IDemoProviderService?
	 * anyhost=true&application=DemoRest&check=false
	 * &deprecated=false
	 * &dubbo=2.0.2
	 * &dynamic=true
	 * &generic=false
	 * &init=false
	 * &interface=com.ruibo.demo.dubboproviderapi.IDemoProviderService
	 * &methods=SayHello&pid=54297
	 * &qos.enable=false
	 * &register.ip=192.168.0.103
	 * &release=2.7.6
	 * &remote.application=DemoProvider
	 * &side=consumer
	 * &sticky=false
	 * &timeout=5000
	 * &timestamp=1587097586792,
	 * cause: org.apache.dubbo.remoting.TimeoutException:
	 * Waiting server-side response
	 * timeout by scan timer. start time: 2020-04-17 12:34:26.036,
	 * end time: 2020-04-17 12:34:31.047, client elapsed: 0 ms, server elapsed: 5010 ms, timeout: 5000 ms, request:
	 * Request [id=2, version=2.0.2, twoway=true, event=false, broken=false, data=null], channel: /192.168.0.103:59492 -> /192.168.0.103:20881
	 *
	 * @param name
	 * @return
	 */
	@GetMapping("/sayHi")
	public Object getHelloResponse(String name) {
		try {
			/**
			 * 1.   org.apache.dubbo.rpc.cluster.support.wrapper.MockClusterInvoker#invoke(org.apache.dubbo.rpc.Invocation)
			 * 2. org.apache.dubbo.rpc.cluster.interceptor.ClusterInterceptor#intercept(org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker, org.apache.dubbo.rpc.Invocation)
			 * // invoker
			 * //interface com.ruibo.demo.dubboproviderapi.IDemoProviderService ->
			 * zookeeper://localhost:2181/org.apache.dubbo.registry.RegistryService?anyhost=true&application=DemoRest&check=false&deprecated=false
			 * &dubbo=2.0.2&dynamic=true&generic=false&init=false
			 * &interface=com.ruibo.demo.dubboproviderapi.IDemoProviderService&methods=SayHello
			 * &pid=60587&qos.enable=false&register.ip=192.168.0.103&release=2.7.6
			 * //retries=0
			 * &remote.application=DemoProvider&retries=0&side=consumer&sticky=false&timeout=5000&timestamp=1587099669723
			 * //cluster.support
			 * 3. org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker#initLoadBalance(java.util.List, org.apache.dubbo.rpc.Invocation)
			 * 4. org.apache.dubbo.rpc.cluster.support.FailoverClusterInvoker#doInvoke(org.apache.dubbo.rpc.Invocation, java.util.List, org.apache.dubbo.rpc.cluster.LoadBalance)
			 * 5. org.apache.dubbo.rpc.Invoker#invoke(org.apache.dubbo.rpc.Invocation)
			 * 6. org.apache.dubbo.rpc.protocol.ProtocolFilterWrapper#buildInvokerChain(org.apache.dubbo.rpc.Invoker, java.lang.String, java.lang.String)
			 * 7. org.apache.dubbo.rpc.filter.ConsumerContextFilter#invoke(org.apache.dubbo.rpc.Invoker, org.apache.dubbo.rpc.Invocation)
			 * 9. org.apache.dubbo.rpc.protocol.dubbo.filter.FutureFilter#invoke(org.apache.dubbo.rpc.Invoker, org.apache.dubbo.rpc.Invocation)
			 * 10. org.apache.dubbo.rpc.protocol.dubbo.filter.FutureFilter#fireInvokeCallback(org.apache.dubbo.rpc.Invoker, org.apache.dubbo.rpc.Invocation)
			 * 12. org.apache.dubbo.rpc.listener.ListenerInvokerWrapper#invoke(org.apache.dubbo.rpc.Invocation)
			 * //AsyncToSyncInvoker
			 * 13. org.apache.dubbo.rpc.protocol.AsyncToSyncInvoker#invoke(org.apache.dubbo.rpc.Invocation)
			 * 14. org.apache.dubbo.rpc.protocol.AbstractInvoker#invoke(org.apache.dubbo.rpc.Invocation)
			 * 15. org.apache.dubbo.rpc.protocol.dubbo.DubboInvoker#doInvoke(org.apache.dubbo.rpc.Invocation)
			 * 16. org.apache.dubbo.remoting.RemotingException:
			 * client(url: dubbo://192.168.0.103:20881/com.ruibo.demo.dubboproviderapi.IDemoProviderService?_client_memo=referencecounthandler.replacewithlazyclient&anyhost=true&application=DemoRest&check=false&codec=dubbo&connect.lazy.initial.state=true&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&heartbeat=60000&init=false&interface=com.ruibo.demo.dubboproviderapi.IDemoProviderService&lazyclient_request_with_warning=true&methods=SayHello&pid=60587&qos.enable=false&reconnect=false&register.ip=192.168.0.103&release=2.7.6&remote.application=DemoProvider&retries=0&send.reconnect=true&side=consumer&sticky=false&timeout=5000&timestamp=1587099640991&warning=true)
			 * failed to connect to server /192.168.0.103:20881, error message is:Connection refused: /192.168.0.103:20881
			 * 17.  2020-04-17 13:18:51.063 ERROR 60587 --- [nio-8001-exec-1] com.ruibo.demo.rest.DemoController
			 * : Failed to invoke the method SayHello in the service com.ruibo.demo.dubboproviderapi.IDemoProviderService.
			 * Tried 1 times of the providers [192.168.0.103:20881] (1/1) from the registry localhost:2181 on the
			 * consumer 192.168.0.103 using the dubbo version 2.7.6. Last error is: Failed to invoke remote
			 * method: SayHello, provider: dubbo://192.168.0.103:20881/com.ruibo.demo.dubboproviderapi.IDemoProviderService?anyhost=true&application=DemoRest&check=false&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&init=false&interface=com.ruibo.demo.dubboproviderapi.IDemoProviderService&methods=SayHello&pid=60587&qos.enable=false&register.ip=192.168.0.103&release=2.7.6&remote.application=DemoProvider&retries=0&side=consumer&sticky=false&timeout=5000&timestamp=1587099640991, cause: client(url: dubbo://192.168.0.103:20881/com.ruibo.demo.dubboproviderapi.IDemoProviderService?_client_memo=referencecounthandler.replacewithlazyclient&anyhost=true&application=DemoRest&check=false&codec=dubbo&connect.lazy.initial.state=true&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&heartbeat=60000&init=false&interface=com.ruibo.demo.dubboproviderapi.IDemoProviderService&lazyclient_request_with_warning=true&methods=SayHello&pid=60587&qos.enable=false&reconnect=false&register.ip=192.168.0.103&release=2.7.6&remote.application=DemoProvider&retries=0&send.reconnect=true&side=consumer&sticky=false&timeout=5000&timestamp=1587099640991&warning=true) failed to connect to server /192.168.0.103:20881, error message is:Connection refused: /192.168.0.103:20881
			 * 18 客户端第二次发起请求 有forbidden逻辑
			 *   if (forbidden) {
			 *             // 1. No service provider 2. Service providers are disabled
			 *             throw new RpcException(RpcException.FORBIDDEN_EXCEPTION, "No provider available from registry " +
			 *                     getUrl().getAddress() + " for service " + getConsumerUrl().getServiceKey() + " on consumer " +
			 *                     NetUtils.getLocalHost() + " use dubbo version " + Version.getVersion() +
			 *                     ", please check status of providers(disabled, not registered or in blacklist).");
			 *         }
			 */
			String resFromProvider = providerService.SayHello(name);
//			String resFromProvider = "";
			String resFromProviderTwo = providerServiceSpeed.SayHello(name);
//		String resFromProviderTwo =  "";
			return ImmutableMap.of(
					"DemoProviderServiceImpl", resFromProvider,
					"DemoProviderServiceSpeedImpl", resFromProviderTwo
			);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "error";

	}

	/**
	 * org.springframework.beans.factory.BeanCreationException:
	 * Error creating bean with name 'demoController':
	 * Injection of @Reference dependencies is failed; nested exception is java.lang.IllegalStateException:
	 * Failed to check the status of the service com.ruibo.demo.dubboproviderapi.IDemoProviderService. No provider available for the service
	 * com.ruibo.demo.dubboproviderapi.IDemoProviderService
	 * from the url zookeeper://localhost:2181/org.apache.dubbo.registry.RegistryService?application=DemoRest&dubbo=2.0.2&init=false&interface=com.ruibo.demo.dubboproviderapi.IDemoProviderService&methods=SayHello&pid=55362&qos.enable=false&register.ip=192.168.0.103&release=2.7.6&retries=0&side=consumer&sticky=false&timeout=5000&timestamp=1587098320356
	 * to the consumer 192.168.0.103 use dubbo version 2.7.6
	 */


	@GetMapping(value = {"/run"})
	public Object run(HttpServletRequest request) {
		DemoRestApplication.run = true;
		return "succ";
	}

	@GetMapping(value = {"/stop"})
	public Object stop(HttpServletRequest request) {
		DemoRestApplication.run = false;
		return "succ";
	}

//创建"视图" 粒度精细到天

	//取天的最大/最小值数据
	//上卷到月 计算滴滴/e代驾的月平均数据

	/**
	 * 使用方式参照
	 * com.guazi.nr.daijia.tools.TimeToolsTest#testGetMonth()
	 *
	 * @param offset
	 * @return
	 */
	static public long getMonthBeginTime(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + offset);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis();
	}
	/**
	 * 使用方式参照
	 * com.guazi.nr.daijia.tools.TimeToolsTest#testGetMonth()
	 *
	 * @param offset
	 * @return
	 */
	static public long getMonthEndTime(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + offset);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		return calendar.getTimeInMillis();
	}

}
