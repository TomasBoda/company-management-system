package com.app.controllers.team;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Team;
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

public class TeamsController extends LoaderPage<Team> implements Initializable {

    @FXML
    private VBox teamsList;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label totalCount;
    @FXML
    private TextField searchField;

    private boolean sortNameReversed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeUtil.initScrollPane(scrollPane);
        addSearchKeyPressListener();
        totalCount.setText(getData().length + " total teams");
        renderData(teamsList, "/components/team-card.fxml");
    }

    @Override
    public Team[] loadData() {
        Response<Team[]> response = Api.teams().getAll();

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    @FXML
    private void addTeam() {
        Router.navigate(Pages.ADD_TEAM);
    }

    @FXML
    private void search() {
        String query = searchField.getText().trim();
        filterBy(team -> team.getName().toLowerCase().contains(query.toLowerCase()));
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
        sortBy(Team::getName, sortNameReversed);
        sortNameReversed = !sortNameReversed;
    }
}
