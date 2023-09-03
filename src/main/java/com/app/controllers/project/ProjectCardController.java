package com.app.controllers.project;

import com.app.model.Project;
import com.app.router.generic.Component;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.datetime.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;

public class ProjectCardController extends Component<Project> {

    @FXML
    private Label name;
    @FXML
    private Label description;
    @FXML
    private Label date;
    @FXML
    private ProgressBar progressBar;

    @Override
    public void onData(Project project) {
        name.setText(project.getName());
        description.setText(project.getDescription());
        progressBar.setProgress(getProgress(project.getStartDate(), project.getEndDate()));

        String startDate = DateUtil.toString(project.getStartDate());
        String endDate = DateUtil.toString(project.getEndDate());
        date.setText(startDate + " - " + endDate);
    }

    @FXML
    private void navigateToEditProjectPage() {
        Router.navigateWithData(Pages.EDIT_PROJECT, getData());
    }

    private double getProgress(Date startDate, Date endDate) {
        Instant now = Instant.now();
        Instant startInstant = new java.util.Date(startDate.getTime()).toInstant();
        Instant endInstant = new java.util.Date(endDate.getTime()).toInstant();

        long totalDurationMillis = Duration.between(startInstant, endInstant).toMillis();
        long elapsedDurationMillis = Duration.between(startInstant, now).toMillis();

        if (elapsedDurationMillis < 0) {
            elapsedDurationMillis = 0;
        } else if (elapsedDurationMillis > totalDurationMillis) {
            elapsedDurationMillis = totalDurationMillis;
        }

        return (double) elapsedDurationMillis / totalDurationMillis;
    }
}
