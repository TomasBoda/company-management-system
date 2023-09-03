package com.app.controllers.project;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.*;
import com.app.router.generic.Component;
import com.app.router.generic.Page;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import com.app.utils.NodeUtil;
import com.app.utils.Validator;
import com.app.utils.converters.TeamConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EditProjectController extends Page<Project> implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private TextField idField;
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
    private VBox pendingTicketsPanel;
    @FXML
    private VBox inProgressTicketsPanel;
    @FXML
    private VBox inReviewTicketsPanel;
    @FXML
    private VBox completedTicketsPanel;
    @FXML
    private ScrollPane scrollPane;

    private Ticket[] tickets;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.setTextFieldToNumeric(budgetField);
        provideDataToTeamField();

        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");
    }

    @Override
    public void onData(Project data) {
        nameLabel.setText("Edit: " + data.getName());

        idField.setText(data.getId());
        nameField.setText(data.getName());
        descriptionField.setText(data.getDescription());
        budgetField.setText(data.getBudget() + "");
        startDateField.setValue(data.getStartDate().toLocalDate());
        endDateField.setValue(data.getEndDate().toLocalDate());

        String teamId = data.getTeamId();
        Team team = fetchProjectTeam(teamId);

        teamField.setValue(team);

        tickets = fetchTickets();
        renderTickets();
    }

    @FXML
    private void addTicket() {
        Router.navigateWithData(Pages.ADD_TICKET, getData());
    }

    @FXML
    private void editProject() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String budget = budgetField.getText().trim();
        Team team = teamField.getValue();
        LocalDate startDate = startDateField.getValue();
        LocalDate endDate = endDateField.getValue();

        if (Validator.areEmpty(id, name, description, budget) || team == null) {
            Dialog.info("Empty fields", "ID, Name, Description, Budget and Team fields cannot be empty.");
            return;
        }

        String teamId = team.getId();

        Response<Boolean> response = Api.projects().edit(new Project(id, teamId, name, description, Integer.parseInt(budget), Date.valueOf(startDate), Date.valueOf(endDate)));

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        Router.navigate(Pages.PROJECTS);
    }

    @FXML
    private void removeProject() {
        String id = idField.getText().trim();

        Response<Boolean> removeProjectResponse = Api.projects().remove(id);

        if (removeProjectResponse.getStatus() != 200) {
            Dialog.info("Database Error", removeProjectResponse.getMessage());
            System.exit(0);
        }

        Response<Boolean> removeProjectTicketsResponse = Api.projects().removeProjectTickets(id);

        if (removeProjectTicketsResponse.getStatus() != 200) {
            Dialog.info("Database Error", removeProjectTicketsResponse.getMessage());
            System.exit(0);
        }

        Router.navigate(Pages.PROJECTS);
    }

    private void renderTicketColumn(Ticket[] tickets, VBox panel) {
        panel.getChildren().clear();

        for (Ticket ticket : tickets) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/ticket.fxml"));
                VBox component = loader.load();
                Component<Ticket> controller = loader.getController();
                controller.setData(ticket);
                panel.getChildren().add(component);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void renderTickets() {
        Ticket[] pendingTickets = Arrays.stream(tickets).filter(ticket -> ticket.getStatus().equals(TicketStatus.PENDING)).toArray(Ticket[]::new);
        Ticket[] inProgressTickets = Arrays.stream(tickets).filter(ticket -> ticket.getStatus().equals(TicketStatus.IN_PROGRESS)).toArray(Ticket[]::new);
        Ticket[] inReviewTickets = Arrays.stream(tickets).filter(ticket -> ticket.getStatus().equals(TicketStatus.IN_REVIEW)).toArray(Ticket[]::new);
        Ticket[] completedTickets = Arrays.stream(tickets).filter(ticket -> ticket.getStatus().equals(TicketStatus.COMPLETED)).toArray(Ticket[]::new);

        renderTicketColumn(pendingTickets, pendingTicketsPanel);
        renderTicketColumn(inProgressTickets, inProgressTicketsPanel);
        renderTicketColumn(inReviewTickets, inReviewTicketsPanel);
        renderTicketColumn(completedTickets, completedTicketsPanel);
    }

    private Ticket[] fetchTickets() {
        Response<Ticket[]> response = Api.tickets().getTicketsByProject(getData().getId());

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    private Team fetchProjectTeam(String teamId) {
        Response<Team> response = Api.teams().get(teamId);

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
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
