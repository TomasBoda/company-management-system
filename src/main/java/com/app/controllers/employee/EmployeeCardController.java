package com.app.controllers.employee;

import com.app.model.Employee;
import com.app.router.generic.Component;
import com.app.main.Pages;
import com.app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmployeeCardController extends Component<Employee> {

    @FXML
    private Label name;
    @FXML
    private Label role;
    @FXML
    private Label employmentType;

    @Override
    public void onData(Employee employee) {
        name.setText(employee.getName());
        role.setText(employee.getRole());
        employmentType.setText(employee.getEmploymentType());
    }

    @FXML
    private void navigateToEditEmployeePage() {
        Router.navigateWithData(Pages.EDIT_EMPLOYEE, getData());
    }
}
