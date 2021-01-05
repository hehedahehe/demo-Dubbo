package com.ruibo.demo.redission;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * /**
 * * CommandLine flags:
 * * -XX:+CMSScavengeBeforeRemark
 * * -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses
 * * -XX:GCLogFileSize=10485760
 * * -XX:+UseGCLogFileRotation
 * * -XX:+HeapDumpOnOutOfMemoryError
 * * -XX:HeapDumpPath=/med/share/nr-daijia-oom.dump -XX:InitialHeapSize=2147483648
 * * -XX:MaxHeapSize=2147483648
 * * -XX:MaxNewSize=348966912
 * * -XX:MaxTenuringThreshold=6
 * * -XX:NewSize=348966912
 * * -XX:NumberOfGCLogFiles=10
 * * -XX:OldPLABSize=16 -XX:OldSize=697933824
 * * -XX:+PrintFlagsFinal
 * * -XX:+PrintGC
 * * -XX:+PrintGCDateStamps
 * * -XX:+PrintGCDetails
 * * -XX:+PrintGCTimeStamps
 * * -XX:+PrintStringTableStatistics
 * * -XX:ThreadStackSize=256
 * * -XX:+UseCMSInitiatingOccupancyOnly
 * * -XX:+UseCompressedClassPointers
 * * -XX:+UseCompressedOops
 * * -XX:ParallelGCThreads=4
 * * -XX:+UseConcMarkSweepGC
 * * -XX:+UseParNewGC
 */
@EnableDubbo
@SpringBootApplication
public class DemoRestApplication {


	public static volatile boolean run = false;

	public static void main(String[] args) {
		SpringApplication.run(DemoRestApplication.class, args);
		new NativeDemo().testGetMaxDirectMemory();
	}

	private static void demoThread() {
		new Thread(() -> {
			System.out.println("线程名称====" + Thread.currentThread());
			while (true) {
				try {
					Thread.sleep(new Random(10).nextInt(5) * 1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				genPersonsData.run();
//				genClassData.run();
//				genConstantsGCMetaSpace.run();
//				genConstantsGCMetaSpace2.run();
//				genConstants.run();
			}
		}).start();
	}

	/**
	 * OOM：heap
	 * 30*10_000_000
	 */
	static Runnable genPersonsData = () -> {
		while (run) {
			System.out.println(new Date().toString() + "====开始加载实例数据");
			List<Person> persons = new LinkedList<>();
			for (int i = 0; i < 1474474; i++) {
				System.out.println(new Date().toString() + "加载数据" + i);
				persons.add(new Person());
			}
			System.out.println(new Date().toString() + "====结束加载实例数据");
		}
	};

	/**
	 * Exception in thread "Thread-3" java.lang.OutOfMemoryError: Metaspace
	 * at java.lang.Class.forName0(Native Method)
	 * at java.lang.Class.forName(Class.java:348)
	 * at net.sf.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:467)
	 * at net.sf.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerat
	 * 测试方法区OOM
	 * <p>
	 * -XX:MaxMetaspaceSize: 设置元空间最大值，默认是-1，即不限制，或者说只限制本地内存大小
	 * -XX:MetaspaceSize: 指定元空间的出初始大小，以字节为单位，达到该值，会触发垃圾收集器进行类型卸载，同时
	 * 来及收集器会对该值进行调整：如果释放了大量空间，就适量降低改制；如果释放了很少空间，那么在不超过-XX:MaxMetaSpaceSize(如果)设置了的话，
	 * 适当提高该值
	 * -XX:MinMetaspaceFreeRatio
	 */
	static Runnable genClassData = () -> {
		while (run) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(Person.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {
				@Override
				public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
					return methodProxy.invokeSuper(o, objects);
				}
			});
			enhancer.create();
		}
	};
	/**
	 * 猜一下它的GC特征
	 * 2020-04-11T19:56:29.627-0800: 61.718: [GC (Allocation Failure) 2020-04-11T19:56:29.627-0800: 61.718: [ParNew: 81924K->6K(92160K), 0.0010230 secs] 93442K->11524K(399360K), 0.0011029 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
	 * 2020-04-11T19:56:29.663-0800: 61.754: [GC (Allocation Failure) 2020-04-11T19:56:29.663-0800: 61.754: [ParNew: 81926K->0K(92160K), 0.0010447 secs] 93444K->11518K(399360K), 0.0011254 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
	 */
	static Runnable genConstantsGCMetaSpace = () -> {
		int i = 0;
		while (run) {
			List<String> strs = new LinkedList<>();
			strs.add(String.valueOf(i++));
		}
	};

	/**
	 * -XX:MaxMetaspaceSize=10m
	 * 猜一下它的GC特征
	 */
	static Runnable genConstantsGCMetaSpace2 = () -> {
		int i = 0;
		List<String> strs = new LinkedList<>();
		while (run) {
			strs.add(String.valueOf(i++));
		}
	};

	/**
	 * 2020-04-11 20:02:23.038  INFO 85443 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 10 ms
	 * java.lang.OutOfMemoryError: Java heap space
	 * Dumping heap to /Users/liruibo/Documents/code/temp/demo-heap.dump ...
	 * Heap dump file created [606240858 bytes in 5.096 secs]
	 * Exception in thread "Thread-3" java.lang.OutOfMemoryError: Java heap space
	 * at java.util.LinkedList.linkLast(LinkedList.java:142)
	 * at java.util.LinkedList.add(LinkedList.java:338)
	 * at com.example.demo.DemoApplication.lambda$static$4(DemoApplication.java:131)
	 * at com.example.demo.DemoApplication$$Lambda$11/6566818.run(Unknown Source)
	 * at com.example.demo.DemoApplication.lambda$main$0(DemoApplication.java:61)
	 * at com.example.demo.DemoApplication$$Lambda$447/1205406622.run(Unknown Source)
	 * at java.lang.Thread.run(Thread.java:748)
	 * <p>
	 * 2020-04-11T20:02:36.629-0800: 40.773: [Full GC (Allocation Failure) 2020-04-11T20:02:36.629-0800: 40.773: [CMS: 307199K->307199K(307200K), 0.6614650 secs] 399359K->399359K(399360K), [Metaspace: 34285K->34285K(1081344K)], 0.6615536 secs] [Times: user=0.65 sys=0.01, real=0.67 secs]
	 * 2020-04-11T20:02:37.297-0800: 41.441: [Full GC (Allocation Failure) 2020-04-11T20:02:37.297-0800: 41.441: [CMS: 307199K->307199K(307200K), 0.7001509 secs] 399359K->399359K(399360K), [Metaspace: 34286K->34286K(1081344K)], 0.7002667 secs] [Times: user=0.68 sys=0.01, real=0.70 secs]
	 * 2020-04-11T20:02:37.998-0800: 42.141: [Full GC (Allocation Failure) 2020-04-11T20:02:37.998-0800: 42.141: [CMS: 307199K->307199K(307200K), 0.6757992 secs] 399359K->399359K(399360K), [Metaspace: 34286K->34286K(1081344K)], 0.6758864 secs] [Times: user=0.66 sys=0.01, real=0.68 secs]
	 * 2020-04-11T20:02:42.394-0800: 46.538: [Full GC (Allocation Failure) 2020-04-11T20:02:42.394-0800: 46.538: [CMS (concurrent mode failure): 307199K->307199K(307200K), 0.6747018 secs] 399359K->399359K(399360K), [Metaspace: 34286K->34286K(1081344K)], 0.6748437 secs] [Times: user=0.66 sys=0.01, real=0.67 secs]
	 * 2020-04-11T20:02:43.069-0800: 47.213: [Full GC (Allocation Failure) 2020-04-11T20:02:43.069-0800: 47.213: [CMS: 307199K->307199K(307200K), 0.6666824 secs] 399359K->399359K(399360K), [Metaspace: 34286K->34286K(1081344K)], 0.6667802 secs] [Times: user=0.66 sys=0.00, real=0.67 secs]
	 * 2020-04-11T20:02:43.736-0800: 47.880: [Full GC (Allocation Failure) 2020-04-11T20:02:43.737-0800: 47.880: [CMS: 307199K->8831K(307200K), 0.1791554 secs] 399359K->8831K(399360K), [Metaspace: 34287K->34287K(1081344K)], 0.1792645 secs] [Times: user=0.18 sys=0.00, real=0.18 secs]
	 * 猜一下它的GC特征
	 * str对象太大 Heap装不下-》fullGC无效 /OOM
	 * metaspace回收不掉
	 */
	static Runnable genGCHeap = () -> {
		List<String> strs = new LinkedList<>();
		int i = 0;
		while (run) {
			strs.add(String.valueOf(i++));
		}
	};


	/**
	 * 测试堆外内存 https://cloud.tencent.com/developer/article/1480371
	 * https://juejin.im/post/5c9ced366fb9a070e344c614
	 */


}
