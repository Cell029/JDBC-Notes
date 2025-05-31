package com.cell.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DruidTest {
    public static void main(String[] args) throws Exception {
        // 用类加载器读取任意 classpath 下的文件
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");
        // 创建属性类对象
        Properties props = new Properties();
        // 将配置文件中的内容封装进属性类对象中
        props.load(in);
        /*System.out.println("==== 配置文件内容 ====");
        props.forEach((key, value) -> System.out.println(key + " = " + value));*/
        // 创建连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(props);
        Connection conn = dataSource.getConnection();
        System.out.println(conn);

        // CRUD...

        // 关闭资源
        conn.close();
    }
}
