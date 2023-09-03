package com.app.utils;

import com.app.utils.converters.EmployeeConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

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

    @SafeVarargs
    public static <T> void provideDataToGenericChoiceBox(ChoiceBox<T> choiceBox, StringConverter<T> converter, T... values) {
        choiceBox.getItems().addAll(values);
        choiceBox.setConverter(converter);
    }

    public static void initScrollPane(ScrollPane scrollPane) {
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");
    }
}
