package com.app.main;

import com.app.api.Api;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static final int MIN_WIDTH = 800;
    public static final int MIN_HEIGHT = 500;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    @Override
    public void start(Stage stage) throws IOException {
        Api.connect();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        scene.getStylesheets().add(Main.class.getResource("/styles/navigation.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("/styles/app.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("/styles/styles.css").toExternalForm());

        stage.setTitle("Company Management System v1.0");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}