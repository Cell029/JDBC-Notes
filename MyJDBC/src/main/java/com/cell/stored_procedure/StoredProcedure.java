package com.cell.stored_procedure;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.CallableStatement;

public class StoredProcedure {
    public static void main(String[] args) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = DbUtils.getConnection();
            String sql = "{call mypro(?, ?)}";
            cs = conn.prepareCall(sql);
            // 给第1个 ? 传值
            cs.setInt(1, 100);
            // 将第2个 ? 注册为出参
            cs.registerOutParameter(2, Types.INTEGER);
            // 执行存储过程
            cs.execute();
            // 通过出参获取结果
            int result = cs.getInt(2);
            System.out.println("计算结果：" + result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.close(conn, cs, null);
        }
    }
}
