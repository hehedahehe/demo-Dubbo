package com.ruibo.demo.dubbo.provider;

import com.ruibo.demo.dubboproviderapi.IDemoProviderService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0")
public class DemoProviderServiceImpl implements IDemoProviderService {

	@Override
	public String SayHello(String word) {
		return "DemoProviderServiceImpl ===" + word;
	}
}

/**
 * 打断点 终止线程
 *
 * 2020-04-17 12:31:25,672 [myid:] - INFO  [NIOWorkerThread-2:ZooKeeperServer@762] - Invalid session 0x100096b78370000 for client /127.0.0.1:58612, probably expired
 * 2020-04-17 12:34:57,516 [myid:] - INFO  [SessionTracker:ZooKeeperServer@398] - Expiring session 0x100096b78370004, timeout of 40000ms exceeded
 * 2020-04-17 12:38:33,311 [myid:] - INFO  [NIOWorkerThread-2:ZooKeeperServer@762] - Invalid session 0x100096b78370004 for client /127.0.0.1:61185, probably expired
 *
 * 重启
 *
 * 关闭provider
 * 2020-04-17 12:44:09.668  INFO 56212 --- [extShutdownHook] .b.c.e.AwaitingNonWebApplicationListener :  [Dubbo] Current Spring Boot Application is about to shutdown...
 * 2020-04-17 12:44:09.674  INFO 56212 --- [extShutdownHook] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
 * 2020-04-17 12:44:09.677  INFO 56212 --- [extShutdownHook] f.a.ReferenceAnnotationBeanPostProcessor : class org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor was destroying!
 * 2020-04-17 12:44:09.682  INFO 56212 --- [tor-Framework-0] o.a.c.f.imps.CuratorFrameworkImpl        : backgroundOperationsLoop exiting
 * 2020-04-17 12:44:09.807  INFO 56212 --- [bboShutdownHook] org.apache.zookeeper.ZooKeeper           : Session: 0x100096b78370008 closed
 * 2020-04-17 12:44:09.807  INFO 56212 --- [ain-EventThread] org.apache.zookeeper.ClientCnxn          : EventThread shut down for session: 0x100096b78370008
 *
 * consumer
 * 2020-04-17 12:44:09.682  INFO 56482 --- [ain-EventThread] o.a.d.r.zookeeper.ZookeeperRegistry      :  [DUBBO] Notify urls for subscribe url consumer://192.168.0.103/com.ruibo.demo.dubboproviderapi.IDemoProviderService?application=DemoRest&category=providers,configurators,routers&dubbo=2.0.2&init=false&interface=com.ruibo.demo.dubboproviderapi.IDemoProviderService&methods=SayHello&pid=56482&qos.enable=false&release=2.7.6&retries=0&side=consumer&sticky=false&timeout=5000&timestamp=1587098614797, urls: [empty://192.168.0.103/com.ruibo.demo.dubboproviderapi.IDemoProviderService?application=DemoRest&category=providers&dubbo=2.0.2&init=false&interface=com.ruibo.demo.dubboproviderapi.IDemoProviderService&methods=SayHello&pid=56482&qos.enable=false&release=2.7.6&retries=0&side=consumer&sticky=false&timeout=5000&timestamp=1587098614797], dubbo version: 2.7.6, current host: 192.168.0.103
 * 2020-04-17 12:44:09.687  INFO 56482 --- [ain-EventThread] o.a.d.r.transport.netty4.NettyChannel    :  [DUBBO] Close netty channel [id: 0x024eb075, L:/192.168.0.103:63043 - R:/192.168.0.103:20881], dubbo version: 2.7.6, current host: 192.168.0.103
 * 2020-04-17 12:44:09.727  INFO 56482 --- [lientWorker-1-1] o.a.d.r.t.netty4.NettyClientHandler      :  [DUBBO] The connection of /192.168.0.103:63043 -> /192.168.0.103:20881 is disconnected., dubbo version: 2.7.6, current host: 192.168.0.103
 *
 * zookeeper nothing
 *
 * consumer call curl "http://localhost:8001/sayHi"
 * 2020-04-17 12:46:59.991 ERROR 56482 --- [nio-8001-exec-1] com.ruibo.demo.rest.DemoController       : No provider available
 * from registry localhost:2181 for service com.ruibo.demo.dubboproviderapi.IDemoProviderService on consumer 192.168.0.103
 * use dubbo version 2.7.6, please check status of providers(disabled, not registered or in blacklist).
 *
 * 重启provider
 2020-04-17 12:48:07.987  INFO 57526 --- [           main] o.a.c.f.imps.CuratorFrameworkImpl        : Starting
 2020-04-17 12:48:07.991  INFO 57526 --- [           main] org.apache.zookeeper.ZooKeeper           : Initiating client connection, connectString=localhost:2181 sessionTimeout=60000 watcher=org.apache.curator.ConnectionState@4a1a256d
 2020-04-17 12:48:07.994  INFO 57526 --- [           main] org.apache.zookeeper.common.X509Util     : Setting -D jdk.tls.rejectClientInitiatedRenegotiation=true to disable client-initiated TLS renegotiation
 2020-04-17 12:48:08.002  INFO 57526 --- [           main] org.apache.zookeeper.ClientCnxnSocket    : jute.maxbuffer value is 4194304 Bytes
 2020-04-17 12:48:08.008  INFO 57526 --- [           main] org.apache.zookeeper.ClientCnxn          : zookeeper.request.timeout value is 0. feature enabled=
 2020-04-17 12:48:08.014  INFO 57526 --- [localhost:2181)] org.apache.zookeeper.ClientCnxn          : Opening socket connection to server localhost/127.0.0.1:2181. Will not attempt to authenticate using SASL (unknown error)
 2020-04-17 12:48:08.018  INFO 57526 --- [           main] o.a.c.f.imps.CuratorFrameworkImpl        : Default schema
 2020-04-17 12:48:08.037  INFO 57526 --- [localhost:2181)] org.apache.zookeeper.ClientCnxn          : Socket connection established, initiating session, client: /127.0.0.1:64899, server: localhost/127.0.0.1:2181
 2020-04-17 12:48:08.046  INFO 57526 --- [localhost:2181)] org.apache.zookeeper.ClientCnxn          : Session establishment complete on server localhost/127.0.0.1:2181, sessionid = 0x100096b7837000a, negotiated timeout = 40000
 2020-04-17 12:48:08.051  INFO 57526 --- [ain-EventThread] o.a.c.f.state.ConnectionStateManager     : State change: CONNECTED
 2020-04-17 12:48:08.062  INFO 57526 --- [ain-EventThread] o.a.c.framework.imps.EnsembleTracker     : New config event received: {}
 2020-04-17 12:48:08.062  INFO 57526 --- [ain-EventThread] o.a.c.framework.imps.EnsembleTracker     : New config event received: {}
 *
 * consumer call curl "http://localhost:8001/sayHi"
 * {"resFromProvider":"provider v2 ===null","resFromProviderTwo":""}%
 *
 * stop provider
 * 020-04-17 12:55:54.698  INFO 57526 --- [extShutdownHook] .b.c.e.AwaitingNonWebApplicationListener :  [Dubbo] Current Spring Boot Application is about to shutdown...
 * 2020-04-17 12:55:54.741  INFO 57526 --- [extShutdownHook] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
 * 2020-04-17 12:55:54.757  INFO 57526 --- [extShutdownHook] f.a.ReferenceAnnotationBeanPostProcessor : class org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor was destroying!
 * 2020-04-17 12:55:54.757  INFO 57526 --- [tor-Framework-0] o.a.c.f.imps.CuratorFrameworkImpl        : backgroundOperationsLoop exiting
 * 2020-04-17 12:55:55.029  INFO 57526 --- [bboShutdownHook] org.apache.zookeeper.ZooKeeper           : Session: 0x100096b7837000a closed
 * 2020-04-17 12:55:55.029  INFO 57526 --- [ain-EventThread] org.apache.zookeeper.ClientCnxn          : EventThread shut down for session: 0x100096b7837000a
 *
 *  consumer call curl "http://localhost:8001/sayHi"
 * 2020-04-17 12:57:22.947 ERROR 56482 --- [nio-8001-exec-3] com.ruibo.demo.rest.DemoController
 * : No provider available from registry localhost:2181 for service com.ruibo.demo.dubboproviderapi.IDemoProviderService on
 * consumer 192.168.0.103 use dubbo version 2.7.6, please check status of providers(disabled, not registered or in blacklist).
 *
 * zk
 * 2020-04-17 12:58:21,530 [myid:] - INFO  [SessionTracker:ZooKeeperServer@398] - Expiring session 0x100096b78370009, timeout of 40000ms exceeded
 *
 *
 */
