package com.app.controllers.employee;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.router.generic.LoaderPage;
import com.app.main.Pages;
import com.app.router.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class EmployeesController extends LoaderPage<Employee> implements Initializable {

    @FXML
    private VBox employeesList;
    @FXML
    private ScrollPane scrollPane;

    private boolean sortNameReversed = false;
    private boolean sortRoleReversed = false;
    private boolean sortEmploymentTypeReversed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        renderData(employeesList, "/components/employee-card.fxml");

        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");
    }

    @Override
    public Employee[] loadData() {
        Response<Employee[]> response = Api.employees().getAll();

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    @FXML
    private void addEmployee() {
        Router.navigate(Pages.ADD_EMPLOYEE);
    }

    @FXML
    private void sortByName() {
        Employee[] employees = getData();

        if (sortNameReversed) {
            Arrays.sort(employees, Collections.reverseOrder(Comparator.comparing(Employee::getName)));
        } else {
            Arrays.sort(employees, Comparator.comparing(Employee::getName));
        }

        setData(employees);
        sortNameReversed = !sortNameReversed;
        rerender();
    }

    @FXML
    private void sortByRole() {
        Employee[] employees = getData();

        if (sortRoleReversed) {
            Arrays.sort(employees, Collections.reverseOrder(Comparator.comparing(Employee::getRole)));
        } else {
            Arrays.sort(employees, Comparator.comparing(Employee::getRole));
        }

        setData(employees);
        sortRoleReversed = !sortRoleReversed;
        rerender();
    }

    @FXML
    private void sortByEmploymentType() {
        Employee[] employees = getData();

        if (sortEmploymentTypeReversed) {
            Arrays.sort(employees, Collections.reverseOrder(Comparator.comparing(Employee::getEmploymentType)));
        } else {
            Arrays.sort(employees, Comparator.comparing(Employee::getEmploymentType));
        }

        setData(employees);
        sortEmploymentTypeReversed = !sortEmploymentTypeReversed;
        rerender();
    }
}
