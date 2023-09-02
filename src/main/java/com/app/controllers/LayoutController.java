package com.app.controllers;

import com.app.router.generic.Page;
import com.app.main.Pages;
import com.app.router.Router;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LayoutController implements Initializable {

    @FXML
    private BorderPane layout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Router.setNavigationController(this);
        Router.navigate(Pages.DASHBOARD);
    }

    @FXML
    private void navigateToDashboardPage() {
        Router.navigate(Pages.DASHBOARD);
    }

    @FXML
    private void navigateToEmployeesPage() {
        Router.navigate(Pages.EMPLOYEES);
    }

    @FXML
    private void navigateToTeamsPage() {
        Router.navigate(Pages.TEAMS);
    }

    @FXML
    private void navigateToProjectsPage() {
        Router.navigate(Pages.PROJECTS);
    }

    @FXML
    private void navigateToLogsPage() {
        Router.navigate(Pages.LOGS);
    }

    public void navigate(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(LayoutController.class.getResource(page));
            Node view = loader.load();
            layout.setCenter(view);
        } catch (IOException e) {
            System.err.println("Cannot navigate to page " + page);
            System.exit(0);
        }
    }

    public <T> void navigateWithData(String page, T data) {
        try {
            FXMLLoader loader = new FXMLLoader((LayoutController.class.getResource(page)));
            Node view = loader.load();
            Page<T> controller = loader.getController();
            controller.setData(data);
            layout.setCenter(view);
        } catch (IOException e) {
            System.err.println("Cannot navigate to page " + page);
            System.exit(0);
        }
    }
}
