package com.app.controllers.employee;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.EmploymentType;
import com.app.model.Role;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import com.app.utils.Generator;
import com.app.utils.NodeUtil;
import com.app.utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

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

    @FXML
    private void addEmployee() {
        String id = Generator.getId();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String role = roleField.getValue();
        String employmentType = employmentTypeField.getValue();
        String salary = salaryField.getText().trim();

        if (Validator.areEmpty(name, email, role, employmentType, salary)) {
            Dialog.info("Empty fields", "Name, E-mail, Employment and Salary fields cannot be empty.");
            return;
        }

        Response<Boolean> response = Api.employees().add(new Employee(id, name, email, role, employmentType, Integer.parseInt(salary)));

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        Router.navigate(Pages.EMPLOYEES);
    }
}
