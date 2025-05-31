package com.cell.firstcode;

import java.sql.*;
import java.util.ResourceBundle;

public class SelectJdbc {
    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1. 注册驱动
            Class.forName(driver);
            // 2. 获取连接
            conn = DriverManager.getConnection(url, user, password);
            // 3. 获取数据库操作对象
            stmt = conn.createStatement();
            // 4. 执行 sql
            String sql = "select * from t_user";
            rs = stmt.executeQuery(sql);
            // 5. 处理查询结果集
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String pwd = rs.getString("password");
                String realname = rs.getString("realname");
                String gender = rs.getString("gender");
                String tel = rs.getString("tel");
                System.out.println(id + " " + name + " " + pwd + " " + realname + " " + gender + " " + tel);
            }

            // 获取元数据信息
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("列名：" + rsmd.getColumnName(i) + "，数据类型：" + rsmd.getColumnTypeName(i) + "，列的长度：" + rsmd.getColumnDisplaySize(i));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
