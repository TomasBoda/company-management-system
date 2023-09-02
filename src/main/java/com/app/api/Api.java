package com.app.api;

import com.app.api.model.*;
import com.app.model.Project;
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

    public static Employees employees() {
        return employees;
    }

    public static Teams teams() {
        return teams;
    }

    public static Projects projects() {
        return projects;
    }

    public static Tickets tickets() {
        return tickets;
    }

    public static Other other() {
        return other;
    }

    public static Logs logs() {
        return logs;
    }

    public static void connect() {
        try {
            //connection = DriverManager.getConnection("jdbc:h2:./database/h2;DB_CLOSE_ON_EXIT=FALSE", "", "");
            connection = DriverManager.getConnection("jdbc:h2:mem:./database/h2", "", "");
            initialize(connection);
        } catch (SQLException e) {
            Dialog.info("Database Error", "Could not connect to the database.");
            System.exit(0);
        }
    }

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
