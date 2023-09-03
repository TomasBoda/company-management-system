package com.app.utils.converters;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Team;
import com.app.utils.Dialog;
import javafx.util.StringConverter;

public class TeamConverter extends StringConverter<Team> {

    private String id;

    @Override
    public String toString(Team team) {
        if (team == null) {
            return null;
        }

        setId(team.getId());

        return team.getName();
    }

    @Override
    public Team fromString(String s) {
        Response<Team> response = Api.teams().get(getId());

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
