package com.cell.pstmt_crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbUtils.getConnection();
            String sql = "update t_emp set ename = ?, job = ?, sal = ? where empno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "李四");
            pstmt.setString(2, "产品经理");
            pstmt.setDouble(3, 5000.0);
            pstmt.setInt(4, 8888);
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
