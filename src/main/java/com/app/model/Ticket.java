package com.app.model;

public class Ticket {

    private String id;
    private String projectId;

    private String title;
    private String description;

    private String status;
    private int points;

    private String assigneeId;
    private String reviewerId;
    private String reporterId;

    public Ticket(String id, String projectId, String title, String description, String status, int points, String assigneeId, String reviewerId, String reporterId) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.points = points;
        this.assigneeId = assigneeId;
        this.reviewerId = reviewerId;
        this.reporterId = reporterId;
    }

    public String getId() {
        return id;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public int getPoints() {
        return points;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public String getReporterId() {
        return reporterId;
    }
}
