package com.app.controllers.ticket;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.*;
import com.app.router.generic.Page;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import com.app.utils.NodeUtil;
import com.app.utils.Validator;
import com.app.utils.converters.EmployeeConverter;
import com.app.utils.converters.ProjectConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTicketController extends Page<Ticket> implements Initializable {

    @FXML
    private Label ticketName;
    @FXML
    private TextField idField;
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
    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.setTextFieldToNumeric(pointsField);
        NodeUtil.provideDataToChoiceBox(statusField, TicketStatus.PENDING, TicketStatus.IN_PROGRESS, TicketStatus.IN_REVIEW, TicketStatus.COMPLETED);
        NodeUtil.initScrollPane(scrollPane);
    }

    @Override
    public void onData(Ticket data) {
        ticketName.setText("Edit: " + data.getTitle());

        idField.setText(data.getId());
        titleField.setText(data.getTitle());
        descriptionField.setText(data.getDescription());
        statusField.setValue(data.getStatus());
        pointsField.setText(data.getPoints() + "");

        Project project = getProject(data.getProjectId());
        NodeUtil.provideDataToGenericChoiceBox(projectField, new ProjectConverter(), project);
        projectField.setValue(project);

        Employee assigneeEmployee = getEmployee(data.getAssigneeId());
        Employee reviewerEmployee = getEmployee(data.getReviewerId());
        Employee reporterEmployee = getEmployee(data.getReporterId());

        assigneeField.setValue(assigneeEmployee);
        reviewerField.setValue(reviewerEmployee);
        reporterField.setValue(reporterEmployee);

        provideEmployeeFieldsWithData(project.getTeamId());
    }

    @FXML
    private void editTicket() {
        String id = idField.getText().trim();
        Project project = projectField.getValue();
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        String status = statusField.getValue();
        String points = pointsField.getText().trim();
        Employee assignee = assigneeField.getValue();
        Employee reviewer = reviewerField.getValue();
        Employee reporter = reporterField.getValue();

        if (Validator.areEmpty(id, title, description, status, points) || project == null || assignee == null || reviewer == null || reporter == null) {
            Dialog.info("Empty Field", "Id, Title, Description, Status, Points, Assignee, Reviewer and Reporter fields cannot be empty.");
            return;
        }

        String projectId = project.getId();

        String assigneeId = assignee.getId();
        String reviewerId = reviewer.getId();
        String reporterId = reporter.getId();

        Response<Boolean> response = Api.tickets().edit(new Ticket(id, projectId, title, description, status, Integer.parseInt(points), assigneeId, reviewerId, reporterId));

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        Router.navigateWithData(Pages.EDIT_PROJECT, getProject(projectId));
    }

    @FXML
    private void removeTicket() {
        String id = idField.getText().trim();

        Response<Boolean> response = Api.tickets().remove(id);

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        Project project = getProject(getData().getProjectId());
        Router.navigateWithData(Pages.EDIT_PROJECT, project);
    }

    private Project getProject(String projectId) {
        Response<Project> response = Api.projects().get(projectId);

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    private Employee getEmployee(String employeeId) {
        Response<Employee> response = Api.employees().get(employeeId);

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    private void provideEmployeeFieldsWithData(String teamId) {
        Response<Employee[]> response = Api.teams().getTeamEmployees(teamId);

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        NodeUtil.provideDataToGenericChoiceBox(assigneeField, new EmployeeConverter(), response.getData());
        NodeUtil.provideDataToGenericChoiceBox(reviewerField, new EmployeeConverter(), response.getData());
        NodeUtil.provideDataToGenericChoiceBox(reporterField, new EmployeeConverter(), response.getData());
    }
}
