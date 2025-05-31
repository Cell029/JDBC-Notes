package com.cell.sql_injection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        System.out.println("欢迎使用用户管理系统，请登录！");
        // 接收用户名和密码
        Scanner scanner = new Scanner(System.in);
        System.out.print("用户名：");
        String loginName = scanner.nextLine();
        System.out.print("密码：");
        String loginPwd = scanner.nextLine();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            stmt = conn.createStatement();
            String sql = "select name from t_login where name = '" + loginName + "' and password = '" + loginPwd + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("name");
                System.out.println("登录成功，欢迎您" + name);
            } else {
                System.out.println("登录失败，用户名不存在或者密码错误。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn, stmt, rs);
        }
    }
}
