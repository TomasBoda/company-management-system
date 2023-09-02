package com.app.model;

public class Employee {

    private String id;
    private String name;
    private String email;
    private String role;

    private String employmentType;
    private int salary;

    public Employee(String id, String name, String email, String role, String employmentType, int salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.employmentType = employmentType;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public int getSalary() {
        return salary;
    }
}
