package com.cell.pstmt_crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchInsert {
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbUtils.getConnection();
            String sql = "insert into t_product(id, name) values (?, ?)";
            pstmt = conn.prepareStatement(sql);
            int count = 0;
            for (int i = 1; i <= 10000; i++) {
                pstmt.setInt(1, i);
                pstmt.setString(2, "product" + i);
                pstmt.addBatch();
            }
            count += pstmt.executeBatch().length;
            System.out.println("插入了" + count + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn, pstmt, null);
        }
        long end = System.currentTimeMillis();
        System.out.println("总耗时" + (end - begin) + "毫秒");
    }
}
