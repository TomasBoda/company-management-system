package com.app.controllers.team;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.Team;
import com.app.router.generic.Component;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TeamCardController extends Component<Team> {

    @FXML
    private Label name;
    @FXML
    private Label employees;

    @Override
    public void onData(Team team) {
        name.setText(team.getName());
        employees.setText(getTeamMemberCount() + " members");
    }

    @FXML
    private void navigateToEditTeamPage() {
        Router.navigateWithData(Pages.EDIT_TEAM, getData());
    }

    private int getTeamMemberCount() {
        Response<Employee[]> response = Api.teams().getTeamEmployees(getData().getId());

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData().length;
    }
}
