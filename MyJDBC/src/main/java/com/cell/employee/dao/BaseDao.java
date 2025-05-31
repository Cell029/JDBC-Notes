package com.cell.employee.dao;

import com.cell.employee.DbUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {
    // 通用的执行insert delete update语句的方法
    public int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            // 获取连接
            conn = DbUtils.getConnection();
            // 获取预编译的数据库操作对象
            ps = conn.prepareStatement(sql);
            // 给 ? 占位符传值
            if(params != null && params.length > 0){
                // 有占位符 ?
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            // 执行SQL语句
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.close(conn, ps, null);
        }
        return count;
    }

    public <T> List<T> executeQuery(String sql, Class<T> clazz, Object... params) {
        List<T> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            // 获取预编译的数据库操作对象
            ps = conn.prepareStatement(sql);
            // 给 ? 传值
            if(params != null && params.length > 0){
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            // 获取查询结果集元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // 获取列树
            int columnCount = rsmd.getColumnCount();
            while(rs.next()){
                // 封装bean对象
                T obj = clazz.newInstance();
                // 给bean对象属性赋值
                /*
                比如现在有一张表：t_user，然后表中有两个字段，一个是 user_id，一个是user_name
                现在javabean是User类，该类中的属性名是：userId,username
                执行这样的SQL语句：select user_id as userId, user_name as username from t_user;
                 */
                for (int i = 1; i <= columnCount; i++) {
                    // 获取查询结果集中的列的名字
                    // 这个列的名字是通过 as 关键字进行了起别名，这个列名就是 bean 的属性名。
                    String fieldName = rsmd.getColumnLabel(i);
                    // 使用反射获取 Java 类中与数据库列名相同的属性（Field 对象）
                    Field declaredField = clazz.getDeclaredField(fieldName);
                    // 打破封装,允许访问 private 字段
                    declaredField.setAccessible(true);
                    // 给属性赋值
                    declaredField.set(obj, rs.getObject(i));
                }
                // 将对象添加到List集合
                list.add(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public <T> T queryOne(Class<T> clazz, String sql, Object... params){
        List<T> list = executeQuery(sql, clazz, params);
        if(list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }
}
