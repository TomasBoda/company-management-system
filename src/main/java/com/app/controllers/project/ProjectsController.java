package com.app.controllers.project;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.Project;
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

public class ProjectsController extends LoaderPage<Project> implements Initializable {

    @FXML
    public VBox projectsList;
    @FXML
    public ScrollPane scrollPane;

    private boolean sortNameReversed = false;
    private boolean sortEndDateReversed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        renderData(projectsList, "/components/project-card.fxml");

        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");
    }

    @Override
    public Project[] loadData() {
        Response<Project[]> response = Api.projects().getAll();

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    @FXML
    private void addProject() {
        Router.navigate(Pages.ADD_PROJECT);
    }

    @FXML
    private void sortByName() {
        Project[] projects = getData();

        if (sortNameReversed) {
            Arrays.sort(projects, Collections.reverseOrder(Comparator.comparing(Project::getName)));
        } else {
            Arrays.sort(projects, Comparator.comparing(Project::getName));
        }

        setData(projects);
        sortNameReversed = !sortNameReversed;
        rerender();
    }

    @FXML
    private void sortByEndDate() {
        Project[] projects = getData();

        if (sortEndDateReversed) {
            Arrays.sort(projects, Collections.reverseOrder(Comparator.comparing(Project::getEndDate)));
        } else {
            Arrays.sort(projects, Comparator.comparing(Project::getEndDate));
        }

        setData(projects);
        sortEndDateReversed = !sortEndDateReversed;
        rerender();
    }
}
