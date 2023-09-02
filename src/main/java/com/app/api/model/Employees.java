package com.app.api.model;

import com.app.api.Response;
import com.app.model.Employee;
import com.app.utils.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Employees {

    private final Connection connection;

    public Employees(Connection connection) {
        this.connection = connection;
        createEmployeesTable();
    }

    public Response<Boolean> add(Employee employee) {
        String query = "INSERT INTO employees (id, name, email, role, employmentType, salary) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employee.getId());
            statement.setString(2, employee.getName());
            statement.setString(3, employee.getEmail());
            statement.setString(4, employee.getRole());
            statement.setString(5, employee.getEmploymentType());
            statement.setInt(6, employee.getSalary());
            statement.executeUpdate();

            Logger.log("Employee added", "Added new employee '" + employee.getName() + "'");

            return new Response<>(200, "Employee added successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Employee couldn't be added: " + e.getMessage(), false);
        }
    }

    public Response<Boolean> edit(Employee employee) {
        String query = "UPDATE employees SET name = ?, email = ?, role = ?, employmentType = ?, salary = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getEmail());
            statement.setString(3, employee.getRole());
            statement.setString(4, employee.getEmploymentType());
            statement.setInt(5, employee.getSalary());
            statement.setString(6, employee.getId());
            statement.executeUpdate();

            Logger.log("Employee edited", "Edited employee '" + employee.getName() + "'");

            return new Response<>(200, "Employee updated successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Employee couldn't be updated: " + e.getMessage(), false);
        }
    }

    public Response<Boolean> remove(String id) {
        String query = "DELETE FROM employees WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.executeUpdate();

            Logger.log("Employee removed", "Removed employee with ID '" + id + "'");

            return new Response<>(200, "Employee removed successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Employee couldn't be removed: " + e.getMessage(), false);
        }
    }

    public Response<Employee> get(String employeeId) {
        String query = "SELECT * FROM employees WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            Employee employee = null;

            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                String employmentType = resultSet.getString("employmentType");
                int salary = resultSet.getInt("salary");

                employee = new Employee(id, name, email, role, employmentType, salary);
            }

            return new Response<>(200, "Employee fetched successfully", employee);
        } catch (SQLException e) {
            return new Response<>(400, "Employee couldn't be fetched");
        }
    }

    public Response<Employee[]> getAll() {
        String query = "SELECT * FROM employees";

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<Employee> employees = new ArrayList<Employee>();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                String employmentType = resultSet.getString("employmentType");
                int salary = resultSet.getInt("salary");

                employees.add(new Employee(id, name, email, role, employmentType, salary));
            }

            return new Response<>(200, "Employees fetched successfully", employees.toArray(Employee[]::new));
        } catch (SQLException e) {
            return new Response<>(400, "Employees couldn't be fetched");
        }
    }

    private void createEmployeesTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS employees (id VARCHAR(32) PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), role VARCHAR(255), employmentType VARCHAR(255), salary INT)";
        try {
            connection.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
