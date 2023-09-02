package com.app.router.generic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

public abstract class LoaderPage<T> {

    private T[] data;

    private VBox panel;
    private String componentPath;

    public LoaderPage() {
        this.data = loadData();
    }

    public abstract T[] loadData();

    public void rerender() {
        renderData(panel, componentPath);
    }

    public void renderData(VBox panel, String componentPath) {
        this.panel = panel;
        this.componentPath = componentPath;

        panel.getChildren().clear();

        for (T element : getData()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(componentPath));
                Node component = loader.load();

                Component<T> controller = loader.getController();
                controller.setData(element);

                panel.getChildren().add(component);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public T[] getData() {
        return data;
    }

    public void setData(T[] data) {
        this.data = data;
    }
}
