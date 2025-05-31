package com.cell.pstmt_crud;

import java.io.*;
import java.sql.*;

public class SelectBlob {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            String sql = "select img from t_img where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                // 获取二进制大对象
                Blob img = rs.getBlob("img");
                // 获取输入流
                InputStream binaryStream = img.getBinaryStream();
                // 创建输出流，该输出流负责写到本地
                OutputStream out = new FileOutputStream("d:/img1.jpg");
                byte[] bytes = new byte[1024];
                int readCount = 0;
                while ((readCount = binaryStream.read(bytes)) != -1) {
                    out.write(bytes, 0, readCount);
                }
                out.flush();
                binaryStream.close();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn, pstmt, rs);
        }
    }
}
