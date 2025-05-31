package com.cell.employee;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;

public class Manager {
    public static void main(String[] args) {
        System.out.println("本系统的功能主要包括：查看员工列表、查看某个员工详细信息、新增员工、修改员工、册除员工");
        System.out.println("请输入对应的功能编号选择功能:");
        System.out.println("[1]查看员工列表");
        System.out.println("[2]查看某个员工详细信息");
        System.out.println("[3]新增员工");
        System.out.println("[4]修改员工");
        System.out.println("[5]册除员工");
        System.out.println("[θ]退出系统");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请输入功能编号:");
            int no = scanner.nextInt();
            scanner.nextLine();
            switch (no) {
                case 1:
                    // 查看员工列表
                    doList();
                    break;
                case 2:
                    // 查看某个员工信息
                    System.out.println("请输入员工 id:");
                    long id = scanner.nextLong();
                    doDetail(id);
                    break;
                case 3:
                    // 新增员工
                    System.out.println("请输入员工姓名:");
                    String name = scanner.nextLine();
                    System.out.println("请输入员工工作:");
                    String job = scanner.nextLine();
                    System.out.println("请输入员工应聘日期:");
                    String date = scanner.nextLine();
                    Date hiredate = Date.valueOf(date);
                    System.out.println("请输入员工薪水:");
                    BigDecimal salary = scanner.nextBigDecimal();
                    scanner.nextLine(); // 清空回车符
                    System.out.println("请输入员工地址:");
                    String address = scanner.nextLine();
                    doSave(name, job, hiredate, salary, address);
                    break;
                case 4:
                    // 修改员工
                    System.out.println("请输入需要修改员工的 id:");
                    long id2 = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("请输入员工姓名:");
                    String name2 = scanner.nextLine();
                    System.out.println("请输入员工工作:");
                    String job2 = scanner.nextLine();
                    System.out.println("请输入员工应聘日期:");
                    String date2 = scanner.nextLine();
                    Date hiredate2 = Date.valueOf(date2);
                    System.out.println("请输入员工薪水:");
                    BigDecimal salary2 = scanner.nextBigDecimal();
                    scanner.nextLine(); // 清空回车符
                    System.out.println("请输入员工地址:");
                    String address2 = scanner.nextLine();
                    doModify(id2, name2, job2, hiredate2, salary2, address2);
                    break;
                case 5:
                    // 删除员工
                    System.out.println("请输入需要删除的员工 id:");
                    long id3 = scanner.nextLong();
                    scanner.nextLine();
                    doDel(id3);
                    break;
                case 0:
                    System.out.println("系统退出成功!");
                    System.exit(0);
                default:
                    System.out.println("输入的功能编号有误,请重新输入:");
            }
        }
    }

    private static void doDel(long id3) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DbUtils.getConnection();
            pstmt = conn.prepareStatement("delete from t_employee where id = ?");
            pstmt.setLong(1, id3);
            pstmt.executeUpdate();
            System.out.println("删除成功!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void doModify(long id2, String name2, String job2, Date hiredate2, BigDecimal salary2, String address2) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DbUtils.getConnection();
            String sql = "update t_employee set name=?,job=?,hiredate=?,salary=?,address=? where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name2);
            pstmt.setString(2, job2);
            pstmt.setDate(3, hiredate2);
            pstmt.setBigDecimal(4, salary2);
            pstmt.setString(5, address2);
            pstmt.setLong(6, id2);
            pstmt.executeUpdate();
            System.out.println("员工信息修改成功!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn, pstmt, null);
        }
    }

    private static void doSave(String name, String job, Date hiredate, BigDecimal salary, String address) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DbUtils.getConnection();
            String sql = "insert into t_employee values(null,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, job);
            pstmt.setDate(3, hiredate);
            pstmt.setBigDecimal(4, salary);
            pstmt.setString(5, address);
            pstmt.executeUpdate();
            System.out.println("员工信息添加成功!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn, pstmt, null);
        }
    }

    private static void doDetail(long id) {
        System.out.println("员工[" + id + "]信息列表如下:");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        System.out.println("id\tname\tjob\thiredate\tsalary\taddress");
        System.out.println("------------------------");
        try {
            conn = DbUtils.getConnection();
            pstmt = conn.prepareStatement("select * from t_employee where id = ?");
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.print(rs.getLong("id") + "\t");
                System.out.print(rs.getString("name") + "\t");
                System.out.print(rs.getString("job") + "\t");
                System.out.print(rs.getString("hiredate") + "\t");
                System.out.print(rs.getBigDecimal("salary") + "\t");
                System.out.print(rs.getString("address") + "\t");
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn, pstmt, rs);
        }
    }

    private static void doList() {
        System.out.println("员工信息列表如下:");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        System.out.println("id\tname\tjob\thiredate\tsalary\taddress");
        System.out.println("------------------------");
        try {
            conn = DbUtils.getConnection();
            pstmt = conn.prepareStatement("select * from t_employee");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.print(rs.getLong("id") + "\t");
                System.out.print(rs.getString("name") + "\t");
                System.out.print(rs.getString("job") + "\t");
                System.out.print(rs.getString("hiredate") + "\t");
                System.out.print(rs.getBigDecimal("salary") + "\t");
                System.out.print(rs.getString("address") + "\t");
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn, pstmt, rs);
        }
    }
}
