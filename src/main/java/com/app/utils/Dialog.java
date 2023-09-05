package com.app.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Dialog {

    /**
     * Displays an informative dialog window to the user
     * @param title title of the dialog window
     * @param text text of the dialog window
     */
    public static void info(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
