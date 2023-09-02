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

public class Tickets {

    private final Connection connection;

    public Tickets(Connection connection) {
        this.connection = connection;
        createTicketsTable();
    }

    public Response<Boolean> add(Ticket ticket) {
        String query = "INSERT INTO tickets (id, projectId, title, description, status, points, assigneeId, reviewerId, reporterId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ticket.getId());
            statement.setString(2, ticket.getProjectId());
            statement.setString(3, ticket.getTitle());
            statement.setString(4, ticket.getDescription());
            statement.setString(5, ticket.getStatus());
            statement.setInt(6, ticket.getPoints());
            statement.setString(7, ticket.getAssigneeId());
            statement.setString(8, ticket.getReviewerId());
            statement.setString(9, ticket.getReporterId());
            statement.executeUpdate();

            Logger.log("Ticket added", "Added new ticket '" + ticket.getTitle() + "' with ID " + ticket.getId());

            return new Response<>(200, "Ticket added successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Ticket couldn't be added: " + e.getMessage(), false);
        }
    }

    public Response<Boolean> edit(Ticket ticket) {
        String query = "UPDATE tickets SET projectId = ?, title = ?, description = ?, status = ?, points = ?, assigneeId = ?, reviewerId = ?, reporterId = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ticket.getProjectId());
            statement.setString(2, ticket.getTitle());
            statement.setString(3, ticket.getDescription());
            statement.setString(4, ticket.getStatus());
            statement.setInt(5, ticket.getPoints());
            statement.setString(6, ticket.getAssigneeId());
            statement.setString(7, ticket.getReviewerId());
            statement.setString(8, ticket.getReporterId());
            statement.setString(9, ticket.getId());
            statement.executeUpdate();

            Logger.log("Ticket edited", "Edited ticket with ID " + ticket.getId());

            return new Response<>(200, "Ticket updated successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Ticket couldn't be updated: " + e.getMessage(), false);
        }
    }

    public Response<Boolean> remove(String ticketId) {
        String query = "DELETE FROM tickets WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ticketId);
            statement.executeUpdate();

            Logger.log("Ticket removed", "Removed ticket with ID " + ticketId);

            return new Response<>(200, "Ticket removed successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Ticket couldn't be removed: " + e.getMessage(), false);
        }
    }

    public Response<Ticket> get(String ticketId) {
        String query = "SELECT * FROM tickets WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ticketId);
            ResultSet resultSet = statement.executeQuery();

            Ticket ticket = null;

            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String projectId = resultSet.getString("projectId");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String status = resultSet.getString("status");
                int points = resultSet.getInt("points");
                String assigneeId = resultSet.getString("assigneeId");
                String reviewerId = resultSet.getString("reviewerId");
                String reporterId = resultSet.getString("reporterId");

                ticket = new Ticket(id, projectId, title, description, status, points, assigneeId, reviewerId, reporterId);
            }

            return new Response<>(200, "Ticket fetched successfully", ticket);
        } catch (SQLException e) {
            return new Response<>(400, "Ticket couldn't be fetched");
        }
    }

    public Response<Ticket[]> getTicketsByProject(String projectId) {
        String query = "SELECT * FROM tickets WHERE projectId = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectId);
            ResultSet resultSet = statement.executeQuery();

            List<Ticket> tickets = new ArrayList<Ticket>();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String status = resultSet.getString("status");
                int points = resultSet.getInt("points");
                String assigneeId = resultSet.getString("assigneeId");
                String reviewerId = resultSet.getString("reviewerId");
                String reporterId = resultSet.getString("reporterId");

                tickets.add(new Ticket(id, projectId, title, description, status, points, assigneeId, reviewerId, reporterId));
            }

            return new Response<>(200, "Tickets fetched successfully", tickets.toArray(Ticket[]::new));
        } catch (SQLException e) {
            return new Response<>(400, "Tickets couldn't be fetched");
        }
    }

    private void createTicketsTable() {
        String query = "CREATE TABLE IF NOT EXISTS tickets (id VARCHAR(32) PRIMARY KEY, projectId VARCHAR(32), title VARCHAR(255), description VARCHAR(1000), status VARCHAR(255), points INT, assigneeId VARCHAR(32), reviewerId VARCHAR(32), reporterId VARCHAR(32))";
        try {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
