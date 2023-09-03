package com.app.controllers.employee;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.router.generic.LoaderPage;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import com.app.utils.NodeUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class EmployeesController extends LoaderPage<Employee> implements Initializable {

    @FXML
    private VBox employeesList;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label totalCount;
    @FXML
    private TextField searchField;

    private boolean sortNameReversed = false;
    private boolean sortRoleReversed = false;
    private boolean sortEmploymentTypeReversed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.initScrollPane(scrollPane);

        totalCount.setText(getData().length + " total employees");
        addSearchKeyPressListener();
        renderData(employeesList, "/components/employee-card.fxml");
    }

    @Override
    public Employee[] loadData() {
        Response<Employee[]> response = Api.employees().getAll();

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    @FXML
    private void search() {
        String query = searchField.getText().trim();
        filterBy(employee -> employee.getName().toLowerCase().contains(query.toLowerCase()));
    }

    private void addSearchKeyPressListener() {
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                search();
            }
        });
    }

    @FXML
    private void addEmployee() {
        Router.navigate(Pages.ADD_EMPLOYEE);
    }

    @FXML
    private void sortByName() {
        sortBy(Employee::getName, sortNameReversed);
        sortNameReversed = !sortNameReversed;
    }

    @FXML
    private void sortByRole() {
        sortBy(Employee::getRole, sortRoleReversed);
        sortRoleReversed = !sortRoleReversed;
    }

    @FXML
    private void sortByEmploymentType() {
        sortBy(Employee::getEmploymentType, sortEmploymentTypeReversed);
        sortEmploymentTypeReversed = !sortEmploymentTypeReversed;
    }
}
