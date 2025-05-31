package com.cell.pstmt_crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbUtils.getConnection();
            String sql = "delete from t_emp where empno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 8888);
            int count = pstmt.executeUpdate();
            if (1 == count) {
                System.out.println("成功更新" + count + "条记录");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.close(conn, pstmt, null);
        }
    }
}
