package com.app.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Log {

    private String id;
    private String title;
    private String description;
    private Timestamp timestamp;

    public Log(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Log(String id, String title, String description, Timestamp timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
