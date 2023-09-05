package com.app.router;

import com.app.controllers.LayoutController;

public class Router {

    private static LayoutController navigationController;

    /**
     * Sets the controller used for navigation throughout the application
     * @param controller controller to be used as the navigation controller
     */
    public static void setNavigationController(LayoutController controller) {
        navigationController = controller;
    }

    /**
     * Navigates to the specified page
     * @param page absolute path of the FXML file/component to be navigated to
     */
    public static void navigate(String page) {
        navigationController.navigate(page);
    }

    /**
     * Navigates to the specified page and provides it with data.
     * The target page must be a descendant of the LoaderPage component
     * @param page absolute path of the FXML file/component to be navigated to
     * @param data generic object representing data to provide to the page
     * @param <T> generic parameter specifying the datatype of the provided data
     */
    public static <T> void navigateWithData(String page, T data) {
        navigationController.navigateWithData(page, data);
    }
}
