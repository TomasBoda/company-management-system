package com.app.controllers.dashboard;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.*;
import com.app.utils.Dialog;
import com.app.utils.NodeUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label employeesLabel;
    @FXML
    private Label teamsLabel;
    @FXML
    private Label projectsLabel;
    @FXML
    private Label revenueLabel;
    @FXML
    private Label spendingsLabel;
    @FXML
    private Label incomeRatio;
    @FXML
    private PieChart projectChart;
    @FXML
    private PieChart salaryChart;
    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.initScrollPane(scrollPane);

        fillGeneralDashboard();
        fillFinancesDashboard();
        fillProjectRevenueDashboard();
        fillRoleSalaryDashboard();
    }

    private void fillGeneralDashboard() {
        int projectCount = fetchProjects().length;
        int employeeCount = fetchEmployees().length;
        int teamCount = fetchTeams().length;

        employeesLabel.setText(employeeCount + " employees");
        teamsLabel.setText(teamCount + " teams");
        projectsLabel.setText(projectCount + " projects");
    }

    private void fillFinancesDashboard() {
        int totalRevenue = fetchTotalRevenue();
        int totalSpendings = fetchTotalSpendings();

        revenueLabel.setStyle("-fx-text-fill: green");
        revenueLabel.setText(totalRevenue + "€");
        spendingsLabel.setStyle("-fx-text-fill: red");
        spendingsLabel.setText("-" + totalSpendings + "€");

        int income = totalRevenue - totalSpendings;
        incomeRatio.setText((income > 0 ? "+" + income : income + "") + "€");

        if (income > 0) {
            incomeRatio.setText("+" + income + "€");
            incomeRatio.setStyle("-fx-text-fill: green");
        } else {
            incomeRatio.setText(income + "€");
            incomeRatio.setStyle("-fx-text-fill: red");
        }
    }

    private void fillProjectRevenueDashboard() {
        Project[] projects = fetchProjects();

        for (Project project : projects) {
            String label = project.getName() + " (" + project.getBudget() + "€)";
            double data = project.getBudget();

            PieChart.Data pieChartEntry = new PieChart.Data(label, data);
            projectChart.getData().add(pieChartEntry);
        }
    }

    private void fillRoleSalaryDashboard() {
        int totalSpendings = fetchTotalSpendings();

        for (String role : Role.ALL) {
            Response<Integer> response = Api.other().getTotalSalaryByRole(role);

            if (response.getStatus() != 200) {
                Dialog.info("Database Error", response.getMessage());
                System.exit(0);
            }

            int totalSalary = response.getData();
            double ratio = (double) totalSalary / totalSpendings;

            String label = role + " (" + totalSalary + "€)";

            PieChart.Data pieChartEntry = new PieChart.Data(label, ratio);
            salaryChart.getData().add(pieChartEntry);
        }
    }

    private Project[] fetchProjects() {
        Response<Project[]> response = Api.projects().getAll();

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    private Employee[] fetchEmployees() {
        Response<Employee[]> response = Api.employees().getAll();

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    private Team[] fetchTeams() {
        Response<Team[]> response = Api.teams().getAll();

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    private int fetchTotalRevenue() {
        Response<Integer> response = Api.other().getTotalRevenue();

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    private int fetchTotalSpendings() {
        Response<Integer> response = Api.other().getTotalSpendings();

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }
}