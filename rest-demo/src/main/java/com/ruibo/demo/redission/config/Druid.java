package com.ruibo.demo.redission.config;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Druid {
	//Druid德鲁伊,据说是魔兽世界中的一个角色,森林女神
	public static DruidDataSource dataSource;

	//1.初始化Druid连接池
	static {
		//1.硬编码初始化Druid连接池
		try {
			dataSource = new DruidDataSource();
			//四个基本属性
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql://localhost:3306/bank");
			dataSource.setUsername("root");
			dataSource.setPassword("123456");
			//其他属性
			//初始大小
			dataSource.setInitialSize(10);
			//最大大小
			dataSource.setMaxActive(50);
			//最小大小
			dataSource.setMinIdle(10);
			//检查时间
			dataSource.setMaxWait(5000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//2.获取连接
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//3.关闭连接
	public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
