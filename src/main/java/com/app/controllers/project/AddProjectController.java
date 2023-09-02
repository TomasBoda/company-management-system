package com.app.controllers.project;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Project;
import com.app.model.Team;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import com.app.utils.Generator;
import com.app.utils.NodeUtil;
import com.app.utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddProjectController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField budgetField;
    @FXML
    private ChoiceBox<String> teamField;
    @FXML
    private DatePicker startDateField;
    @FXML
    private DatePicker endDateField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.setTextFieldToNumeric(budgetField);
        provideDataToTeamField();
    }

    @FXML
    private void addProject() {
        String id = Generator.getId();
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String budget = budgetField.getText().trim();
        String team = teamField.getValue();
        LocalDate startDate = startDateField.getValue();
        LocalDate endDate = endDateField.getValue();

        if (Validator.areEmpty(id, name, description, budget, team)) {
            Dialog.show("Empty fields", "ID, Name, Description and Team fields cannot be empty.");
            return;
        }

        String teamId = team.split("-")[1];

        Response<Boolean> response = Api.projects().add(new Project(id, teamId, name, description, Integer.parseInt(budget), Date.valueOf(startDate), Date.valueOf(endDate)));

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        Router.navigate(Pages.PROJECTS);
    }

    private void provideDataToTeamField() {
        Response<Team[]> response = Api.teams().getAll();

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        List<String> teams = new ArrayList<>();

        for (Team team : response.getData()) {
            teams.add(team.getName() + "-" + team.getId());
        }

        NodeUtil.provideDataToChoiceBox(teamField, teams.toArray(String[]::new));
    }
}