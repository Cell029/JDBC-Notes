package com.cell.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class DbUtils {
    private static DataSource dataSource;

    static {

        try {
            // 用类加载器读取任意 classpath 下的文件
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");
            // 创建属性类对象
            Properties props = new Properties();
            // 将配置文件中的内容封装进属性类对象中
            props.load(in);
            dataSource = DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private DbUtils() {

    }

    // 通过连接池获取连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
