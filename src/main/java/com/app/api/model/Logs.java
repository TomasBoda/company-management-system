package com.app.api.model;

import com.app.api.Response;
import com.app.model.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Logs {

    private final Connection connection;

    public Logs(Connection connection) {
        this.connection = connection;
        createLogsTable();
    }

    /**
     * Adds a new log to the database
     * @param log Log object to be added to the database
     * @return API Response object
     */
    public Response<Boolean> add(Log log) {
        String query = "INSERT INTO logs (id, title, description) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, log.getId());
            statement.setString(2, log.getTitle());
            statement.setString(3, log.getDescription());
            statement.executeUpdate();

            return new Response<>(200, "Log added successfully", true);
        } catch (SQLException e) {
            return new Response<>(400, "Log couldn't be added: " + e.getMessage(), false);
        }
    }

    /**
     * Retrieves the complete list of logs from the database
     * @return API Response object
     */
    public Response<Log[]> getAll() {
        String query = "SELECT * FROM logs";

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<Log> logs = new ArrayList<>();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");

                logs.add(new Log(id, title, description, timestamp));
            }

            return new Response<>(200, "Logs fetched successfully", logs.toArray(Log[]::new));
        } catch (SQLException e) {
            return new Response<>(400, "Logs couldn't be fetched");
        }
    }

    private void createLogsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS logs (id VARCHAR(32) PRIMARY KEY, title VARCHAR(255), description VARCHAR(1500), timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        try {
            connection.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
