package com.app.utils.converters;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.Project;
import com.app.utils.Dialog;
import javafx.util.StringConverter;

public class ProjectConverter extends StringConverter<Project> {

    private String id;

    @Override
    public String toString(Project project) {
        if (project == null) {
            return null;
        }

        setId(project.getId());

        return project.getName();
    }

    @Override
    public Project fromString(String s) {
        Response<Project> response = Api.projects().get(getId());

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    private String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }
}
