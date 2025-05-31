package com.cell.pstmt_crud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertBlob {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        InputStream in = null;

        try {
            conn = DbUtils.getConnection();
            String sql = "insert into t_img(img) values(?)";
            pstmt = conn.prepareStatement(sql);
            in = new FileInputStream("f:/图片/OIP-C.jfif");
            pstmt.setBlob(1, in);
            int count = pstmt.executeUpdate();
            System.out.println("插入了" + count + "条记录");
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            DbUtils.close(conn, pstmt, null);
        }
    }
}
