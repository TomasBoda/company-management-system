package com.app.controllers.ticket;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.*;
import com.app.router.generic.Page;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import com.app.utils.Generator;
import com.app.utils.NodeUtil;
import com.app.utils.Validator;
import com.app.utils.converters.EmployeeConverter;
import com.app.utils.converters.ProjectConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTicketController extends Page<Project> implements Initializable {

    @FXML
    private ChoiceBox<Project> projectField;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ChoiceBox<String> statusField;
    @FXML
    private TextField pointsField;
    @FXML
    private ChoiceBox<Employee> assigneeField;
    @FXML
    private ChoiceBox<Employee> reviewerField;
    @FXML
    private ChoiceBox<Employee> reporterField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.setTextFieldToNumeric(pointsField);
        NodeUtil.provideDataToChoiceBox(statusField, TicketStatus.PENDING, TicketStatus.IN_PROGRESS, TicketStatus.IN_REVIEW, TicketStatus.COMPLETED);
    }

    @Override
    public void onData(Project data) {
        NodeUtil.provideDataToGenericChoiceBox(projectField, new ProjectConverter(), data);
        projectField.setValue(data);
        provideEmployeeFieldsWithData();
    }

    @FXML
    private void addTicket() {
        String id = Generator.getId();
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        String status = statusField.getValue();
        String points = pointsField.getText().trim();
        Employee assignee = assigneeField.getValue();
        Employee reviewer = reviewerField.getValue();
        Employee reporter = reporterField.getValue();

        if (Validator.areEmpty(id, title, description, status, points) || assignee == null || reviewer == null || reporter == null) {
            Dialog.info("Empty Field", "Id, Title, Description, Status, Points, Assignee, Reviewer and Reporter fields cannot be empty.");
            return;
        }

        String projectId = getData().getId();

        String assigneeId = assignee.getId();
        String reviewerId = reviewer.getId();
        String reporterId = reporter.getId();

        Response<Boolean> response = Api.tickets().add(new Ticket(id, projectId, title, description, status, Integer.parseInt(points), assigneeId, reviewerId, reporterId));

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        Router.navigateWithData(Pages.EDIT_PROJECT, getData());
    }

    private void provideStatusFieldWithData() {
        ObservableList<String> list = FXCollections.observableArrayList();

        list.add(TicketStatus.PENDING);
        list.add(TicketStatus.IN_PROGRESS);
        list.add(TicketStatus.IN_REVIEW);
        list.add(TicketStatus.COMPLETED);

        statusField.setItems(list);
    }

    private void provideEmployeeFieldsWithData() {
        Response<Employee[]> response = Api.teams().getTeamEmployees(getData().getTeamId());

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        NodeUtil.provideDataToGenericChoiceBox(assigneeField, new EmployeeConverter(), response.getData());
        NodeUtil.provideDataToGenericChoiceBox(reviewerField, new EmployeeConverter(), response.getData());
        NodeUtil.provideDataToGenericChoiceBox(reporterField, new EmployeeConverter(), response.getData());
    }
}
