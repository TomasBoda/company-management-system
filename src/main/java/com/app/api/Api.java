package com.app.api;

import com.app.api.model.*;
import com.app.utils.Dialog;

import java.sql.*;

public class Api {

    private static Connection connection;

    private static Employees employees;
    private static Teams teams;
    private static Projects projects;
    private static Tickets tickets;
    private static Other other;
    private static Logs logs;

    /**
     * Retrieves the employees API object
     * @return employees API object
     */
    public static Employees employees() {
        return employees;
    }

    /**
     * Retrieves the teams API object
     * @return teams API object
     */
    public static Teams teams() {
        return teams;
    }

    /**
     * Retrieves the projects API object
     * @return projects API object
     */
    public static Projects projects() {
        return projects;
    }

    /**
     * Retrieves the tickets API object
     * @return tickets API object
     */
    public static Tickets tickets() {
        return tickets;
    }

    /**
     * Retrieves the other API object
     * @return other API object
     */
    public static Other other() {
        return other;
    }

    /**
     * Retrieves the logs API object
     * @return logs API object
     */
    public static Logs logs() {
        return logs;
    }

    /**
     * Connects to the H2 embedded database
     */
    public static void connect() {
        try {
            Class.forName("org.h2.Driver");

            connection = DriverManager.getConnection("jdbc:h2:./database/h2;DB_CLOSE_ON_EXIT=FALSE", "", "");
            initialize(connection);
        } catch (SQLException | ClassNotFoundException e) {
            Dialog.info("Database Error", "Could not connect to the database.");
            System.exit(0);
        }
    }

    /**
     * Disconnects from the H2 embedded database
     */
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            Dialog.info("Database Error", "Could not disconnect from the database.");
            System.exit(0);
        }
    }

    private static void initialize(Connection connection) {
        employees = new Employees(connection);
        teams = new Teams(connection);
        projects = new Projects(connection);
        tickets = new Tickets(connection);
        other = new Other(connection);
        logs = new Logs(connection);
    }
}
