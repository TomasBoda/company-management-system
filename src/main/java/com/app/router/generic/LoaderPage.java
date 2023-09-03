package com.app.router.generic;

import com.app.model.Employee;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class LoaderPage<T> {

    private T[] data;

    private VBox panel;
    private String componentPath;

    public LoaderPage() {
        this.data = loadData();
    }

    public void reloadData() {
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

    public void filterBy(Predicate<T> predicate) {
        T[] data = loadData();
        List<T> filtered = Arrays.stream(data).filter(predicate).toList();

        if (data.length == filtered.size()) {
            reloadData();
            rerender();
            return;
        }

        T[] array = (T[]) new Object[filtered.size()];

        for (int i = 0; i < filtered.size(); i++) {
            array[i] = filtered.get(i);
        }

        setData(array);
        rerender();
    }

    public <U extends Comparable<? super U>> void sortBy(Function<? super T, ? extends U> function, boolean reversed) {
        T[] data = getData();

        Comparator<T> comparator = reversed ? Collections.reverseOrder(Comparator.comparing(function)) : Comparator.comparing(function);
        Arrays.sort(data, comparator);

        setData(data);
        rerender();
    }

    public T[] getData() {
        return data;
    }

    public void setData(T[] data) {
        this.data = data;
    }
}
