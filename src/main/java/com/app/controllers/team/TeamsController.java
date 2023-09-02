package com.app.controllers.team;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Project;
import com.app.model.Team;
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

public class TeamsController extends LoaderPage<Team> implements Initializable {

    @FXML
    private VBox teamsList;
    @FXML
    private ScrollPane scrollPane;

    private boolean sortNameReversed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        renderData(teamsList, "/components/team-card.fxml");

        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");
    }

    @Override
    public Team[] loadData() {
        Response<Team[]> response = Api.teams().getAll();

        if (response.getStatus() != 200) {
            System.out.println(response.getMessage());
            System.exit(0);
        }

        return response.getData();
    }

    @FXML
    private void addTeam() {
        Router.navigate(Pages.ADD_TEAM);
    }

    @FXML
    private void sortByName() {
        Team[] teams = getData();

        if (sortNameReversed) {
            Arrays.sort(teams, Collections.reverseOrder(Comparator.comparing(Team::getName)));
        } else {
            Arrays.sort(teams, Comparator.comparing(Team::getName));
        }

        setData(teams);
        sortNameReversed = !sortNameReversed;
        rerender();
    }
}
