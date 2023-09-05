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

    /**
     * Sets an FXML TextField component to only accept numeric input
     * @param field TextField component to set to numeric to
     */
    public static void setTextFieldToNumeric(final TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Provides an FXML ChoiceBox component with static textual values
     * @param choiceBox ChoiceBox component to provide the data to
     * @param values array of textual values to provide to the ChoiceBox
     */
    public static void provideDataToChoiceBox(ChoiceBox<String> choiceBox, String... values) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(Arrays.asList(values));
        choiceBox.setItems(list);
    }

    /**
     * Provides a generic FXML ChoiceBox component with data
     * @param choiceBox ChoiceBox component to provide the data to
     * @param converter StringConverter object used for converting the ChoiceBox input data to strings for visual representation
     * @param values array of values to provide to the ChoiceBox
     * @param <T> generic parameter representing the datatype of the ChoiceBox data
     */
    @SafeVarargs
    public static <T> void provideDataToGenericChoiceBox(ChoiceBox<T> choiceBox, StringConverter<T> converter, T... values) {
        choiceBox.getItems().addAll(values);
        choiceBox.setConverter(converter);
    }

    /**
     * Initializes an FXML ScrollPane component to a default state
     * @param scrollPane ScrollPane component to initialize
     */
    public static void initScrollPane(ScrollPane scrollPane) {
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
    }
}
