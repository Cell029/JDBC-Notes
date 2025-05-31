package com.cell.pstmt_crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Insert {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbUtils.getConnection();
            String sql = "insert into t_emp(empno,ename,sal,comm,job,mgr,hiredate,deptno) values(?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 8888);
            pstmt.setString(2, "张三");
            pstmt.setDouble(3, 10000.0);
            pstmt.setDouble(4, 500.0);
            pstmt.setString(5, "销售员");
            pstmt.setInt(6, 7369);
            LocalDate localDate = LocalDate.parse("2025-01-01");
            pstmt.setDate(7, java.sql.Date.valueOf(localDate));
            pstmt.setInt(8, 10);
            int count = pstmt.executeUpdate();
            if (1 == count) {
                System.out.println("成功更新" + count + "条记录");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn, pstmt, null);
        }
    }
}
