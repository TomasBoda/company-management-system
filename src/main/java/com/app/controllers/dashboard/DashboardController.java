package com.app.controllers.dashboard;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.Log;
import com.app.model.Project;
import com.app.model.Team;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

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
    private ProgressBar incomeRatio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillGeneralDashboard();
    }

    private void fillGeneralDashboard() {
        int projectCount = fetchProjectCount();
        int employeeCount = fetchEmployeeCount();
        int teamCount = fetchTeamCount();

        int totalRevenue = fetchTotalRevenue();
        int totalSpendings = fetchTotalSpendings();

        projectsLabel.setText(projectCount + "");
        employeesLabel.setText(employeeCount + "");
        teamsLabel.setText(teamCount + "");
        revenueLabel.setText(totalRevenue + "€");
        spendingsLabel.setText(totalSpendings + "€");

        incomeRatio.setProgress((double) totalRevenue / totalSpendings);
    }

    private int fetchProjectCount() {
        Response<Project[]> response = Api.projects().getAll();

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        return response.getData().length;
    }

    private int fetchEmployeeCount() {
        Response<Employee[]> response = Api.employees().getAll();

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        return response.getData().length;
    }

    private int fetchTeamCount() {
        Response<Team[]> response = Api.teams().getAll();

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        return response.getData().length;
    }

    private int fetchTotalRevenue() {
        Response<Integer> response = Api.other().getTotalRevenue();

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    private int fetchTotalSpendings() {
        Response<Integer> response = Api.other().getTotalSpendings();

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }
}