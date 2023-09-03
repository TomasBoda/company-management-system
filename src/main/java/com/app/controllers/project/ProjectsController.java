package com.app.controllers.project;

import com.app.api.Api;
import com.app.api.Response;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectsController extends LoaderPage<Project> implements Initializable {

    @FXML
    private VBox projectsList;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label totalCount;
    @FXML
    private TextField searchField;

    private boolean sortNameReversed = false;
    private boolean sortEndDateReversed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.initScrollPane(scrollPane);
        totalCount.setText(getData().length + " total projects");
        addSearchKeyPressListener();
        renderData(projectsList, "/components/project-card.fxml");
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
    private void search() {
        String query = searchField.getText().trim();
        filterBy(project -> project.getName().toLowerCase().contains(query.toLowerCase()));
    }

    private void addSearchKeyPressListener() {
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                search();
            }
        });
    }

    @FXML
    private void sortByName() {
        sortBy(Project::getName, sortNameReversed);
        sortNameReversed = !sortNameReversed;
    }

    @FXML
    private void sortByEndDate() {
        sortBy(Project::getEndDate, sortEndDateReversed);
        sortEndDateReversed = !sortEndDateReversed;
    }
}
