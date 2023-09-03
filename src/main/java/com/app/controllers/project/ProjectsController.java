package com.app.controllers.project;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Employee;
import com.app.model.Project;
import com.app.router.generic.LoaderPage;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
import com.app.utils.NodeUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ProjectsController extends LoaderPage<Project> implements Initializable {

    @FXML
    private VBox projectsList;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label totalCount;

    private boolean sortNameReversed = false;
    private boolean sortEndDateReversed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        renderData(projectsList, "/components/project-card.fxml");
        NodeUtil.initScrollPane(scrollPane);

        totalCount.setText(getData().length + " total projects");
    }

    @Override
    public Project[] loadData() {
        Response<Project[]> response = Api.projects().getAll();

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
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
