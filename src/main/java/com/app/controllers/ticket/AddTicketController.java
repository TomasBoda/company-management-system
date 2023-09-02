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
    private TextField projectField;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ChoiceBox<String> statusField;
    @FXML
    private TextField pointsField;
    @FXML
    private ChoiceBox<String> assigneeField;
    @FXML
    private ChoiceBox<String> reviewerField;
    @FXML
    private ChoiceBox<String> reporterField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.setTextFieldToNumeric(pointsField);
        provideEmployeeFieldsWithData();
        provideStatusFieldWithData();
    }

    @Override
    public void onData(Project data) {
        projectField.setText(data.getName() + "-" + data.getId());
    }

    @FXML
    private void addTicket() {
        String id = Generator.getId();
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        String status = statusField.getValue();
        String points = pointsField.getText().trim();
        String assignee = assigneeField.getValue();
        String reviewer = reviewerField.getValue();
        String reporter = reporterField.getValue();

        if (Validator.areEmpty(id, title, description, status, points, assignee, reviewer, reporter)) {
            Dialog.show("Empty Field", "Id, Title, Description, Status, Points, Assignee, Reviewer and Reporter fields cannot be empty.");
            return;
        }

        String projectId = getData().getId();

        String assigneeId = assignee.split("-")[1];
        String reviewerId = reviewer.split("-")[1];
        String reporterId = reporter.split("-")[1];

        Response<Boolean> response = Api.tickets().add(new Ticket(id, projectId, title, description, status, Integer.parseInt(points), assigneeId, reviewerId, reporterId));

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
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
        ObservableList<String> list = FXCollections.observableArrayList();

        Response<Employee[]> response = Api.employees().getAll();

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        for (Employee employee : response.getData()) {
            list.add(employee.getName() + "-" + employee.getId());
        }

        assigneeField.setItems(list);
        reviewerField.setItems(list);
        reporterField.setItems(list);
    }
}
