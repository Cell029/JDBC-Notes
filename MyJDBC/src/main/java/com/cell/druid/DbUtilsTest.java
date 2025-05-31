package com.cell.druid;

import java.sql.Connection;
import java.sql.SQLException;

public class DbUtilsTest {
    public static void main(String[] args) {
        Connection connection1 = null;
        Connection connection2 = null;
        Connection connection3 = null;
        Connection connection4 = null;
        Connection connection5 = null;
        try {
            connection1 = DbUtils.getConnection();
            System.out.println(connection1);
            connection1.close();
            connection2 = DbUtils.getConnection();
            System.out.println(connection2);
            connection2.close();
            connection3 = DbUtils.getConnection();
            System.out.println(connection3);
            connection3.close();
            connection4 = DbUtils.getConnection();
            System.out.println(connection4);
            connection4.close();
            connection5 = DbUtils.getConnection();
            System.out.println(connection5);
            connection5.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
