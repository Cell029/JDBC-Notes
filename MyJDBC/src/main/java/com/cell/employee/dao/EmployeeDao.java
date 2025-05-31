package com.cell.employee.dao;

import com.cell.employee.pojo.Employee;

import java.util.List;

public class EmployeeDao extends BaseDao {
    public int insert(Employee employee) {
        String sql = "insert into t_employee(name,job,salary,hiredate,address) values(?,?,?,?,?)";
        int count = executeUpdate(sql, employee.getName(), employee.getJob(), employee.getSalary(), employee.getHiredate(), employee.getAddress());
        return count;
    }

    public int update(Employee employee) {
        String sql = "update t_employee set name=?, job=?, salary=?, hiredate=?, address=? where id=?";
        return executeUpdate(sql, employee.getName(), employee.getJob(), employee.getSalary(), employee.getHiredate(), employee.getAddress(), employee.getId());
    }

    public int delete(int id) {
        String sql = "delete from t_employee where id=?";
        return executeUpdate(sql, id);
    }

    public Employee getEmployeeById(int id) {
        String sql = "select * from t_employee where id=?";
        List<Employee> employees = executeQuery(sql, Employee.class, id);
        return employees.get(0);
    }

    public List<Employee> getAllEmployees() {
        String sql = "select * from t_employee";
        List<Employee> employees = executeQuery(sql, Employee.class);
        return employees;
    }
}
