package com.app.controllers.team;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.Team;
import com.app.router.generic.Page;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import com.app.utils.NodeUtil;
import com.app.utils.Validator;
import com.app.utils.converters.EmployeeConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class EditTeamController extends Page<Team> implements Initializable {

    @FXML
    private Label teamName;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<Employee> employeeSelection;
    @FXML
    private VBox employeesList;
    @FXML
    private ScrollPane scrollPane;

    private ArrayList<Employee> teamEmployees;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.initScrollPane(scrollPane);
        addEmployeesFieldSelectionListener();
        provideDataToEmployeesField();
    }

    @Override
    public void onData(Team data) {
        teamName.setText("Edit: " + data.getName());

        idField.setText(data.getId());
        nameField.setText(data.getName());

        teamEmployees = fetchTeamEmployees();
        renderTeamEmployees();
    }

    private ArrayList<Employee> fetchTeamEmployees() {
        Response<Employee[]> response = Api.teams().getTeamEmployees(getData().getId());

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return new ArrayList<>(Arrays.asList(response.getData()));
    }

    @FXML
    private void editTeam() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();

        if (Validator.areEmpty(id, name) || teamEmployees.isEmpty()) {
            Dialog.info("Empty field", "Name and Employees fields cannot be empty.");
            return;
        }

        Response<Boolean> response = Api.teams().edit(new Team(id, name));

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        Response<Boolean> removeTeamEmployeesResponse = Api.teams().removeTeamEmployees(id);

        if (removeTeamEmployeesResponse.getStatus() != 200) {
            Dialog.info("Database Error", removeTeamEmployeesResponse.getMessage());
            System.exit(0);
        }

        for (Employee employee : teamEmployees) {
            Response<Boolean> addEmployeeResponse = Api.teams().addEmployeeToTeam(id, employee.getId());

            if (addEmployeeResponse.getStatus() != 200) {
                Dialog.info("Database Error", response.getMessage());
                System.exit(0);
            }
        }

        Router.navigate(Pages.TEAMS);
    }

    @FXML
    private void removeTeam() {
        String id = idField.getText().trim();

        Response<Boolean> removeTeamResponse = Api.teams().remove(id);

        if (removeTeamResponse.getStatus() != 200) {
            Dialog.info("Database Error", removeTeamResponse.getMessage());
            System.exit(0);
        }

        Response<Boolean> removeTeamEmployeesResponse = Api.teams().removeTeamEmployees(id);

        if (removeTeamEmployeesResponse.getStatus() != 200) {
            Dialog.info("Database Error", removeTeamEmployeesResponse.getMessage());
            System.exit(0);
        }

        Router.navigate(Pages.TEAMS);
    }

    private void renderTeamEmployees() {
        employeesList.getChildren().clear();

        for (Employee employee : teamEmployees) {
            HBox wrapper = new HBox();
            wrapper.setSpacing(20);
            wrapper.setAlignment(Pos.CENTER_LEFT);

            Button removeButton = new Button("Remove");
            removeButton.setOnAction(event -> {
                removeEmployee(employee.getId());
                renderTeamEmployees();
            });

            Label label = new Label(employee.getName() + " (" + employee.getRole() + ") - " + employee.getEmploymentType());

            wrapper.getChildren().add(removeButton);
            wrapper.getChildren().add(label);

            employeesList.getChildren().add(wrapper);
        }
    }

    private void addEmployeesFieldSelectionListener() {
        employeeSelection.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (isEmployeeAlreadyAdded(newValue)) {
                    return;
                }

                teamEmployees.add(newValue);
                employeeSelection.setValue(null);
                renderTeamEmployees();
            }
        });
    }

    private void removeEmployee(String id) {
        teamEmployees.removeIf(employee -> employee.getId().equals(id));
    }

    private boolean isEmployeeAlreadyAdded(Employee newEmployee) {
        for (Employee employee : teamEmployees) {
            if (employee.getId().equals(newEmployee.getId())) {
                return true;
            }
        }

        return false;
    }

    private void provideDataToEmployeesField() {
        Response<Employee[]> response = Api.employees().getAll();

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        NodeUtil.provideDataToGenericChoiceBox(employeeSelection, new EmployeeConverter(), response.getData());
    }
}
