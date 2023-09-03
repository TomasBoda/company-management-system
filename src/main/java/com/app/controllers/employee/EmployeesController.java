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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
        renderData(employeesList, "/components/employee-card.fxml");
        NodeUtil.initScrollPane(scrollPane);

        totalCount.setText(getData().length + " total employees");
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

        if (query.isEmpty()) {
            setData(loadData());
            rerender();
            return;
        }

        Employee[] employees = getData();
        List<Employee> filteredList = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(employee);
            }
        }

        setData(filteredList.toArray(Employee[]::new));
        rerender();
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
