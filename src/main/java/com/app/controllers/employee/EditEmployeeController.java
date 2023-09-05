package com.app.controllers.employee;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.EmploymentType;
import com.app.model.Role;
import com.app.router.generic.Page;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import com.app.utils.NodeUtil;
import com.app.utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditEmployeeController extends Page<Employee> implements Initializable {

    @FXML
    private Label employeeName;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private ChoiceBox<String> roleField;
    @FXML
    private ChoiceBox<String> employmentTypeField;
    @FXML
    private TextField salaryField;
    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.initScrollPane(scrollPane);
        NodeUtil.setTextFieldToNumeric(salaryField);
        NodeUtil.provideDataToChoiceBox(roleField, Role.ALL);
        NodeUtil.provideDataToChoiceBox(employmentTypeField, EmploymentType.ALL);
    }

    @Override
    public void onData(Employee data) {
        employeeName.setText("Edit: " + data.getName());

        idField.setText(data.getId());
        nameField.setText(data.getName());
        emailField.setText(data.getEmail());
        roleField.setValue(data.getRole());
        employmentTypeField.setValue(data.getEmploymentType());
        salaryField.setText(data.getSalary() + "");
    }

    @FXML
    private void editEmployee() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String role = roleField.getValue();
        String employmentType = employmentTypeField.getValue();
        String salary = salaryField.getText().trim();

        if (Validator.areEmpty(name, email, salary) || role == null || employmentType == null) {
            Dialog.info("Empty fields", "Name, E-mail, Employment and Salary fields cannot be empty.");
            return;
        }

        if (!Validator.isValidEmail(email)) {
            Dialog.info("Format error", "E-mail is in incorrect form");
            return;
        }

        Response<Boolean> response = Api.employees().edit(new Employee(id, name, email, role, employmentType, Integer.parseInt(salary)));

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        Router.navigate(Pages.EMPLOYEES);
    }

    @FXML
    private void removeEmployee() {
        String id = idField.getText().trim();

        Response<Boolean> response = Api.employees().remove(id);

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        Router.navigate(Pages.EMPLOYEES);
    }
}

