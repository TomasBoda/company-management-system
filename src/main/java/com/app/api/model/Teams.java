package com.app.api.model;

import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.Project;
import com.app.model.Team;
import com.app.utils.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Teams {

    private final Connection connection;

    public Teams(Connection connection) {
        this.connection = connection;
        createTeamsTable();
        createTeamEmployeesTable();
    }

    public Response<Boolean> add(Team team) {
        String query = "INSERT INTO teams (id, name) VALUES (?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, team.getId());
            statement.setString(2, team.getName());
            statement.executeUpdate();

            Logger.log("Team added", "Added new team " + team.getName() + " with ID " + team.getId());

            return new Response<>(200, "Team added successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Team couldn't be added: " + e.getMessage(), false);
        }
    }

    public Response<Boolean> edit(Team team) {
        String query = "UPDATE teams SET name = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, team.getName());
            statement.setString(2, team.getId());
            statement.executeUpdate();

            Logger.log("Team edited", "Edited team " + team.getName() + " with ID " + team.getId());

            return new Response<>(200, "Team updated successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Team couldn't be updated: " + e.getMessage(), false);
        }
    }

    public Response<Boolean> remove(String teamId) {
        String query = "DELETE FROM teams WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, teamId);
            statement.executeUpdate();

            Logger.log("Team removed", "Removed team with ID " + teamId);

            return new Response<>(200, "Team removed successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Team couldn't be removed: " + e.getMessage(), false);
        }
    }

    public Response<Team> get(String teamId) {
        String query = "SELECT * FROM teams WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, teamId);
            ResultSet resultSet = statement.executeQuery();

            Team team = null;

            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");

                team = new Team(id, name);
            }

            return new Response<>(200, "Team fetched successfully", team);
        } catch (SQLException e) {
            return new Response<>(400, "Team couldn't be fetched");
        }
    }

    public Response<Team[]> getAll() {
        String query = "SELECT * FROM teams";

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<Team> teams = new ArrayList<Team>();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");

                teams.add(new Team(id, name));
            }

            return new Response<>(200, "Teams fetched successfully", teams.toArray(Team[]::new));
        } catch (SQLException e) {
            return new Response<>(400, "Teams couldn't be fetched");
        }
    }

    public Response<Boolean> addEmployeeToTeam(String teamId, String employeeId) {
        String query = "INSERT INTO teamEmployees (teamId, employeeId) VALUES (?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, teamId);
            statement.setString(2, employeeId);
            statement.executeUpdate();

            Logger.log("Employee added to Team", "Added employee with ID " + employeeId + " to team with ID " + teamId);

            return new Response<>(200, "Team Employee added successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Team Employee couldn't be added: " + e.getMessage(), false);
        }
    }

    public Response<Boolean> removeTeamEmployees(String teamId) {
        String query = "DELETE FROM teamEmployees WHERE teamId = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, teamId);
            statement.executeUpdate();

            Logger.log("Team employees removed", "Removed employees from team with ID " + teamId);

            return new Response<>(200, "Team Employees removed successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Team Employees couldn't be removed: " + e.getMessage(), false);
        }
    }

    public Response<Employee[]> getTeamEmployees(String teamId) {
        String query = "SELECT employees.* FROM employees JOIN teamEmployees ON employees.id = teamEmployees.employeeId WHERE teamEmployees.teamId = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, teamId);
            ResultSet resultSet = statement.executeQuery();

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

            return new Response<>(200, "Team Employees fetched successfully", employees.toArray(Employee[]::new));
        } catch (SQLException e) {
            return new Response<>(400, "Team Employees couldn't be fetched");
        }
    }

    private void createTeamsTable() {
        String query = "CREATE TABLE IF NOT EXISTS teams (id VARCHAR(32) PRIMARY KEY, name VARCHAR(255))";
        try {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTeamEmployeesTable() {
        String query = "CREATE TABLE IF NOT EXISTS teamEmployees (teamId VARCHAR(32), employeeId VARCHAR(32))";
        try {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
