package com.app.controllers.dashboard;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.*;
import com.app.utils.Dialog;
import com.app.utils.NodeUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;

import java.net.StandardSocketOptions;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
        fillGeneralDashboard();
        fillFinancesDashboard();
        fillProjectRevenueDashboard();
        fillRoleSalaryDashboard();

        NodeUtil.initScrollPane(scrollPane);
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
            PieChart.Data slice = new PieChart.Data(project.getName() + " (" + project.getBudget() + "€)", project.getBudget());
            projectChart.getData().add(slice);
        }
    }

    private void fillRoleSalaryDashboard() {
        int totalSpendings = fetchTotalSpendings();

        int softwareDeveloperSalarySum = Api.other().getTotalSalaryByRole(Role.SOFTWARE_DEVELOPER).getData();
        int softwareTesterSalarySum = Api.other().getTotalSalaryByRole(Role.SOFTWARE_TESTER).getData();
        int productOwnerSalarySum = Api.other().getTotalSalaryByRole(Role.PRODUCT_OWNER).getData();
        int productManagerSalarySum = Api.other().getTotalSalaryByRole(Role.PRODUCT_MANAGER).getData();
        int scrumMasterSalarySum = Api.other().getTotalSalaryByRole(Role.SCRUM_MASTER).getData();

        double softwareDeveloperRatio = (double) softwareDeveloperSalarySum / totalSpendings;
        double softwareTesterRatio = (double) softwareTesterSalarySum / totalSpendings;
        double projectOwnerRatio = (double) productOwnerSalarySum / totalSpendings;
        double projectManagerRatio = (double) productManagerSalarySum / totalSpendings;
        double scrumMasterRatio = (double) scrumMasterSalarySum / totalSpendings;

        PieChart.Data slice1 = new PieChart.Data(Role.SOFTWARE_DEVELOPER + " (" + softwareDeveloperSalarySum + "€)", softwareDeveloperRatio);
        PieChart.Data slice2 = new PieChart.Data(Role.SOFTWARE_TESTER + " (" + softwareTesterSalarySum + "€)", softwareTesterRatio);
        PieChart.Data slice3 = new PieChart.Data(Role.PRODUCT_OWNER + " (" + productOwnerSalarySum + "€)", projectOwnerRatio);
        PieChart.Data slice4 = new PieChart.Data(Role.PRODUCT_MANAGER + " (" + productManagerSalarySum + "€)", projectManagerRatio);
        PieChart.Data slice5 = new PieChart.Data(Role.SCRUM_MASTER + " (" + scrumMasterSalarySum + "€)", scrumMasterRatio);

        salaryChart.getData().addAll(slice1, slice2, slice3, slice4, slice5);
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