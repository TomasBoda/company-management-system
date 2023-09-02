package com.app.model;

import java.sql.Date;

public class Project {

    private String id;
    private String teamId;
    private String name;
    private String description;
    private int budget;
    private Date startDate;
    private Date endDate;

    public Project(String id, String teamId, String name, String description, int budget, Date startDate, Date endDate) {
        this.id = id;
        this.teamId = teamId;
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getBudget() {
        return budget;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
