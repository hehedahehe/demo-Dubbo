package com.ruibo.demo.redission;

import sun.misc.JavaNioAccess;
import sun.misc.SharedSecrets;
import sun.misc.VM;

import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;

public class NativeDemo {

	private BufferPoolMXBean getDirectBufferPoolMBean() {
		return ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class)
				.stream()
				.filter(e -> e.getName().equals("direct"))
				.findFirst()
				.get();
	}

	private JavaNioAccess.BufferPool getNioBufferPool() {
		return SharedSecrets.getJavaNioAccess().getDirectBufferPool();
	}

	/**
	 * -XX:MaxDirectMemorySize=60M
	 * jcmd 3088 VM.native_memory scale=MB
	 */
	public void testGetMaxDirectMemory() {
		/**
		 * 1014.0
		 * 60.0
		 * 50.0
		 * 50.0
		 */
//		ByteBuffer.allocateDirect(50 * 1024 * 1024);
		/**
		 * Exception in thread "main" java.lang.reflect.InvocationTargetException
		 * 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		 * 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
		 * 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
		 * 	at java.lang.reflect.Method.invoke(Method.java:498)
		 * 	at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:48)
		 * 	at org.springframework.boot.loader.Launcher.launch(Launcher.java:87)
		 * 	at org.springframework.boot.loader.Launcher.launch(Launcher.java:51)
		 * 	at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:52)
		 * Caused by: java.lang.OutOfMemoryError: Direct buffer memory
		 * 	at java.nio.Bits.reserveMemory(Bits.java:694)
		 * 	at java.nio.DirectByteBuffer.<init>(DirectByteBuffer.java:123)
		 * 	at java.nio.ByteBuffer.allocateDirect(ByteBuffer.java:311)
		 * 	at com.ruibo.demo.rest.NativeDemo.testGetMaxDirectMemory(NativeDemo.java:29)
		 * 	at com.ruibo.demo.rest.DemoRestApplication.main(DemoRestApplication.java:54)
		 * 	... 8 more
		 */
		ByteBuffer.allocateDirect(50 * 1024 * 1024);
		System.out.println(Runtime.getRuntime().maxMemory() / 1024.0 / 1024.0);
		System.out.println(VM.maxDirectMemory() / 1024.0 / 1024.0);
		System.out.println(getDirectBufferPoolMBean().getTotalCapacity() / 1024.0 / 1024.0);
		System.out.println(getNioBufferPool().getTotalCapacity() / 1024.0 / 1024.0);
	}
	/**
	 * 73118:
	 *
	 * Native Memory Tracking:
	 *
	 * Total: reserved=2481MB, committed=313MB
	 * -                 Java Heap (reserved=1024MB, committed=116MB)
	 *                             (mmap: reserved=1024MB, committed=116MB)
	 *
	 * -                     Class (reserved=1057MB, committed=36MB)
	 *                             (classes #6483)
	 *                             (malloc=1MB #9596)
	 *                             (mmap: reserved=1056MB, committed=35MB)
	 *
	 * -                    Thread (reserved=33MB, committed=33MB)
	 *                             (thread #34)
	 *                             (stack: reserved=33MB, committed=33MB)
	 *
	 * -                      Code (reserved=246MB, committed=12MB)
	 *                             (malloc=2MB #3585)
	 *                             (mmap: reserved=244MB, committed=10MB)
	 *
	 * -                        GC (reserved=12MB, committed=9MB)
	 *                             (malloc=8MB #205)
	 *                             (mmap: reserved=4MB, committed=0MB)
	 *
	 * -                  Internal (reserved=51MB, committed=51MB)
	 *                             (malloc=51MB #9409)
	 *
	 * -                    Symbol (reserved=9MB, committed=9MB)
	 *                             (malloc=7MB #72443)
	 *                             (arena=1MB #1)
	 *
	 * -    Native Memory Tracking (reserved=1MB, committed=1MB)
	 *                             (tracking overhead=1MB)
	 *
	 * -                   Unknown (reserved=47MB, committed=47MB)
	 *                             (mmap: reserved=47MB, committed=47MB)
	 */

}
