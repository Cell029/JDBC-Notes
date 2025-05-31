package com.cell.employee.pojo;

public class Employee {
    private Long id;
    private String name;
    private String job;
    private Double salary;
    private String hiredate;
    private String address;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", salary=" + salary +
                ", hiredate='" + hiredate + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Employee() {
    }

    public Employee(Long id, String name, String job, Double salary, String hiredate, String address) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.hiredate = hiredate;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
