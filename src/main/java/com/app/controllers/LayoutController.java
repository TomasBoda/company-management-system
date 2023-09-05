package com.app.controllers;

import com.app.router.generic.Page;
import com.app.main.Pages;
import com.app.router.Router;
import com.app.utils.Dialog;
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

    /**
     * Navigates the user to the Dashboard page
     */
    @FXML
    private void navigateToDashboardPage() {
        Router.navigate(Pages.DASHBOARD);
    }

    /**
     * Navigates the user to the Employees page
     */
    @FXML
    private void navigateToEmployeesPage() {
        Router.navigate(Pages.EMPLOYEES);
    }

    /**
     * Navigates the user to the Teams page
     */
    @FXML
    private void navigateToTeamsPage() {
        Router.navigate(Pages.TEAMS);
    }

    /**
     * Navigates the user to the Projects page
     */
    @FXML
    private void navigateToProjectsPage() {
        Router.navigate(Pages.PROJECTS);
    }

    /**
     * Navigates the user to the Logs page
     */
    @FXML
    private void navigateToLogsPage() {
        Router.navigate(Pages.LOGS);
    }

    /**
     * Navigates the user to the page specified
     * @param page absolute path of the FXML file/component to navigate the user to
     */
    public void navigate(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(LayoutController.class.getResource(page));
            Node view = loader.load();
            layout.setCenter(view);
        } catch (IOException e) {
            Dialog.info("Navigation error", "Cannot navigate to page " + page);
            System.exit(0);
        }
    }

    /**
     * Navigates the user to the page specified and provides the page with the specified data
     * @param page absolute path of the FXML file/component to navigate the user to
     * @param data data object to provide to the page
     * @param <T> generic parameter representing the datatype of the provided data
     */
    public <T> void navigateWithData(String page, T data) {
        try {
            FXMLLoader loader = new FXMLLoader((LayoutController.class.getResource(page)));
            Node view = loader.load();
            Page<T> controller = loader.getController();
            controller.setData(data);
            layout.setCenter(view);
        } catch (IOException e) {
            Dialog.info("Navigation error", "Cannot navigate to page " + page);
            System.exit(0);
        }
    }
}
