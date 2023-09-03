package com.app.model;

public class TicketStatus {

    public static String PENDING = "Pending";
    public static String IN_PROGRESS = "In Progress";
    public static String IN_REVIEW = "In Review";
    public static String COMPLETED = "Completed";

    public static final String[] ALL = new String[] { PENDING, IN_PROGRESS, IN_REVIEW, COMPLETED };
}
