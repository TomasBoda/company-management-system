package com.app.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class NodeUtil {

    public static void setTextFieldToNumeric(final TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public static void provideDataToChoiceBox(ChoiceBox<String> choiceBox, String... values) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(Arrays.asList(values));
        choiceBox.setItems(list);
    }
}
