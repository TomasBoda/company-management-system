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
import com.app.utils.converters.TeamConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddProjectController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField budgetField;
    @FXML
    private ChoiceBox<Team> teamField;
    @FXML
    private DatePicker startDateField;
    @FXML
    private DatePicker endDateField;
    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.initScrollPane(scrollPane);
        NodeUtil.setTextFieldToNumeric(budgetField);
        provideDataToTeamField();
    }

    @FXML
    private void addProject() {
        String id = Generator.getId();
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String budget = budgetField.getText().trim();
        Team team = teamField.getValue();
        LocalDate startDate = startDateField.getValue();
        LocalDate endDate = endDateField.getValue();

        if (Validator.areEmpty(id, name, description, budget) || team == null || startDate == null || endDate == null) {
            Dialog.info("Empty fields", "ID, Name, Description, Team, Start Date or End Date fields cannot be empty.");
            return;
        }

        String teamId = team.getId();

        Response<Boolean> response = Api.projects().add(new Project(id, teamId, name, description, Integer.parseInt(budget), Date.valueOf(startDate), Date.valueOf(endDate)));

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        Router.navigate(Pages.PROJECTS);
    }

    private void provideDataToTeamField() {
        Response<Team[]> response = Api.teams().getAll();

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        NodeUtil.provideDataToGenericChoiceBox(teamField, new TeamConverter(), response.getData());
    }
}
