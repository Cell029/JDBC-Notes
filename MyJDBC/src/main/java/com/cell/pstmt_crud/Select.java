package com.cell.pstmt_crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Select {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            String sql = "select ename from t_emp where ename like ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "_主%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("ename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn, pstmt, rs);
        }
    }
}
