package com.app.api.model;

import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.Project;
import com.app.model.Team;
import com.app.model.Ticket;
import com.app.utils.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Projects {

    private final Connection connection;

    public Projects(Connection connection) {
        this.connection = connection;
        createProjectsTable();
    }

    /**
     * Adds a new project to the database
     * @param project Project object to be added to the database
     * @return API Response object
     */
    public Response<Boolean> add(Project project) {
        String query = "INSERT INTO projects (id, teamId, name, description, budget, startDate, endDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, project.getId());
            statement.setString(2, project.getTeamId());
            statement.setString(3, project.getName());
            statement.setString(4, project.getDescription());
            statement.setInt(5, project.getBudget());
            statement.setDate(6, project.getStartDate());
            statement.setDate(7, project.getEndDate());
            statement.executeUpdate();

            Logger.log("Project added", "Added a new project " + project.getName() + " with ID " + project.getId());

            return new Response<>(200, "Project added successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Project couldn't be added: " + e.getMessage(), false);
        }
    }

    /**
     * Updates an existing project in the database
     * @param project Project object to be updated in the database
     * @return API Response object
     */
    public Response<Boolean> edit(Project project) {
        String query = "UPDATE projects SET teamId = ?, name = ?, description = ?, budget = ?, startDate = ?, endDate = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, project.getTeamId());
            statement.setString(2, project.getName());
            statement.setString(3, project.getDescription());
            statement.setInt(4, project.getBudget());
            statement.setDate(5, project.getStartDate());
            statement.setDate(6, project.getEndDate());
            statement.setString(7, project.getId());
            statement.executeUpdate();

            Logger.log("Project edited", "Edited project " + project.getName() + " with ID " + project.getId());

            return new Response<>(200, "Project updated successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Project couldn't be updated: " + e.getMessage(), false);
        }
    }

    /**
     * Removes an existing project from the database
     * @param projectId ID of the project to be removed from the database
     * @return API Response object
     */
    public Response<Boolean> remove(String projectId) {
        String query = "DELETE FROM projects WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectId);
            statement.executeUpdate();

            Logger.log("Project removed", "Removed project with ID " + projectId);

            return new Response<>(200, "Project removed successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Project couldn't be removed: " + e.getMessage(), false);
        }
    }

    /**
     * Retrieves a project from the database by its ID
     * @param id ID of the project to be retrieved from the database
     * @return API Response object
     */
    public Response<Project> get(String id) {
        String query = "SELECT * FROM projects WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            Project project = null;

            if (resultSet.next()) {
                String teamId = resultSet.getString("teamId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int budget = resultSet.getInt("budget");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");

                project = new Project(id, teamId, name, description, budget, startDate, endDate);
            }

            return new Response<>(200, "Project fetched successfully", project);
        } catch (SQLException e) {
            return new Response<>(400, "Project couldn't be fetched");
        }
    }

    /**
     * Retrieves the complete list of projects from the database
     * @return API Response object
     */
    public Response<Project[]> getAll() {
        String query = "SELECT * FROM projects";

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<Project> projects = new ArrayList<Project>();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String teamId = resultSet.getString("teamId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int budget = resultSet.getInt("budget");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");

                projects.add(new Project(id, teamId, name, description, budget, startDate, endDate));
            }

            return new Response<>(200, "Projects fetched successfully", projects.toArray(Project[]::new));
        } catch (SQLException e) {
            return new Response<>(400, "Projects couldn't be fetched");
        }
    }

    /**
     * Removes all tickets associated to a project from the database
     * @param projectId ID of the project to remove the tickets from
     * @return API Response object
     */
    public Response<Boolean> removeProjectTickets(String projectId) {
        String query = "DELETE FROM tickets WHERE projectId = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectId);
            statement.executeUpdate();

            Logger.log("Project tickets removed", "Removed tickets from project with ID " + projectId);

            return new Response<>(200, "Project Tickets removed successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Project Tickets couldn't be removed: " + e.getMessage(), false);
        }
    }

    private void createProjectsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS projects (id VARCHAR(32) PRIMARY KEY, teamId VARCHAR(32) DEFAULT NULL, name VARCHAR(255), description VARCHAR(1000), budget INT, startDate DATE, endDate DATE)";
        try {
            connection.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
