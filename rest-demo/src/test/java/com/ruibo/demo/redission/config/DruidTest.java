package com.ruibo.demo.redission.config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

class DruidTest {
	/**
	 * com.alibaba.druid.pool.GetConnectionTimeoutException: wait millis 5004, active 50, maxActive 50, creating 0
	 * 	at com.alibaba.druid.pool.DruidDataSource.getConnectionInternal(DruidDataSource.java:1512)
	 * 	at com.alibaba.druid.pool.DruidDataSource.getConnectionDirect(DruidDataSource.java:1255)
	 * 	at com.alibaba.druid.pool.DruidDataSource.getConnection(DruidDataSource.java:1235)
	 * 	at com.alibaba.druid.pool.DruidDataSource.getConnection(DruidDataSource.java:1225)
	 *
	 * 	com.alibaba.druid.pool.DruidDataSource#getConnectionInternal(long)
	 * @throws Exception
	 */
	@Test
	void getMaxConnection() throws Exception{
		//超过最大限制或报"TimeoutException",每打开一个关闭一个就不会发生异常
		for (int i = 0; i < 51; i++) {
			Connection connection = Druid.getConnection();
			// 创建Statement语句
//			Statement statement = connection.createStatement();
//			// 执行查询语句
//			ResultSet rs = statement.executeQuery("select * from account limit 3");
//			// 遍历查询结果集
//			while (rs.next()) {
//				String name = rs.getString("name");
//				System.out.println(name);
//			}
			System.out.println(connection.toString() + "\n------------------------------------");
		}
	}

	@Test
	void getMaxConnection_2() {
		//超过最大限制或报"TimeoutException",每打开一个关闭一个就不会发生异常
		for (int i = 0; i < 51; i++) {
			Connection connection = Druid.getConnection();
			System.out.println(connection.toString() + "\n------------------------------------");
			Druid.closeAll(connection, null, null);
		}
	}

}