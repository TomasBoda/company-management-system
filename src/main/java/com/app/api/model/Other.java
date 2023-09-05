package com.app.api.model;

import com.app.api.Response;
import com.app.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Other {

    private final Connection connection;

    public Other(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves the total company spendings amount from the database
     * @return API Response object
     */
    public Response<Integer> getTotalSpendings() {
        String query = "SELECT SUM(salary) AS totalSalary FROM employees";

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            int totalSalary = 0;

            if (resultSet.next()) {
                totalSalary = resultSet.getInt("totalSalary");
            }

            return new Response<>(200, "Total salary fetched successfully", totalSalary);
        } catch (SQLException e) {
            return new Response<>(400, "Total salary couldn't be fetched");
        }
    }

    /**
     * Retrieves the total company revenue amount from the database
     * @return API Response object
     */
    public Response<Integer> getTotalRevenue() {
        String query = "SELECT SUM(budget) AS totalBudget FROM projects";

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            int totalBudget = 0;

            if (resultSet.next()) {
                totalBudget = resultSet.getInt("totalBudget");
            }

            return new Response<>(200, "Total budget fetched successfully", totalBudget);
        } catch (SQLException e) {
            return new Response<>(400, "Total budget couldn't be fetched");
        }
    }

    /**
     * Retrieves the total salary amount by role from the database
     * @param role role to retrieve the salary by
     * @return API Response object
     */
    public Response<Integer> getTotalSalaryByRole(String role) {
        String query = "SELECT SUM(salary) AS totalSalary FROM employees WHERE role = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();

            int totalSalary = 0;

            if (resultSet.next()) {
                totalSalary = resultSet.getInt("totalSalary");
            }

            return new Response<>(200, "Total salary fetched successfully", totalSalary);
        } catch (SQLException e) {
            return new Response<>(400, "Total salary couldn't be fetched");
        }
    }
}
