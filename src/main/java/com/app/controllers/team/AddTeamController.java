package com.app.controllers.team;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.Team;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import com.app.utils.Generator;
import com.app.utils.NodeUtil;
import com.app.utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class AddTeamController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<String> employeeSelection;
    @FXML
    private VBox employeesList;

    private ArrayList<Employee> teamEmployees = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        provideDataToEmployeesField();
        addEmployeesFieldSelectionListener();
    }

    @FXML
    private void addTeam() {
        String id = Generator.getId();
        String name = nameField.getText().trim();

        if (Validator.areEmpty(name) || teamEmployees.isEmpty()) {
            Dialog.show("Empty field", "Name and Employees fields cannot be empty.");
            return;
        }

        Response<Boolean> response = Api.teams().add(new Team(id, name));

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        for (Employee employee : teamEmployees) {
            Response<Boolean> addEmployeeResponse = Api.teams().addEmployeeToTeam(id, employee.getId());

            if (addEmployeeResponse.getStatus() != 200) {
                System.out.println(response.getMessage());
                System.exit(0);
            }
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

            Label label = new Label(employee.getName() + "-" + employee.getId());

            wrapper.getChildren().add(removeButton);
            wrapper.getChildren().add(label);

            employeesList.getChildren().add(wrapper);
        }
    }

    private void addEmployeesFieldSelectionListener() {
        employeeSelection.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String id = newValue.split("-")[1];

                if (isEmployeeAlreadyAdded(id)) {
                    return;
                }

                Response<Employee> response = Api.employees().get(id);

                if (response.getStatus() != 200) {
                    System.out.println(response.getMessage());
                    System.exit(0);
                }

                teamEmployees.add(response.getData());
                employeeSelection.setValue(null);
                renderTeamEmployees();
            }
        });
    }

    private void removeEmployee(String id) {
        teamEmployees.removeIf(employee -> employee.getId().equals(id));
    }

    private boolean isEmployeeAlreadyAdded(String id) {
        for (Employee employee : teamEmployees) {
            if (employee.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    private void provideDataToEmployeesField() {
        Response<Employee[]> response = Api.employees().getAll();

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        List<String> employees = new ArrayList<>();

        for (Employee employee : response.getData()) {
            employees.add(employee.getName() + "-" + employee.getId());
        }

        NodeUtil.provideDataToChoiceBox(employeeSelection, employees.toArray(String[]::new));
    }
}
