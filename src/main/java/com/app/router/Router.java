package com.app.router;

import com.app.controllers.LayoutController;

public class Router {

    private static LayoutController navigationController;

    public static void setNavigationController(LayoutController controller) {
        navigationController = controller;
    }

    public static void navigate(String page) {
        navigationController.navigate(page);
    }

    public static <T> void navigateWithData(String page, T data) {
        navigationController.navigateWithData(page, data);
    }
}
