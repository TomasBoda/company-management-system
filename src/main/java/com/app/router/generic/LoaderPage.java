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

    /**
     * Reloads the page data using the loadData function
     */
    public void reloadData() {
        this.data = loadData();
    }

    /**
     * Function that specifies what data is loaded to the page and how
     * @return array of the loaded data
     */
    public abstract T[] loadData();

    /**
     * Rerenders the page using the renderData function
     */
    public void rerender() {
        renderData(panel, componentPath);
    }

    /**
     * Renders the page's data
     * @param panel wrapper component around the rendered data
     * @param componentPath absolute path to the FXML file/component which should be used as a template for one data item to be rendered
     */
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

    /**
     * Filters the page's data by a predicate, sets the filtered data to the page's data and rerenders the page
     * @param predicate predicate using which the data should be filtered
     */
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

    /**
     * Sorts the page's data by a comparing function, sets the sorted data to the page's data and rerenders the page
     * @param function comparing function to sort the data by
     * @param reversed flag determining whether the sorted data should be reversed or not
     * @param <U> generic parameter for the comparing function
     */
    public <U extends Comparable<? super U>> void sortBy(Function<? super T, ? extends U> function, boolean reversed) {
        T[] data = getData();

        Comparator<T> comparator = reversed ? Collections.reverseOrder(Comparator.comparing(function)) : Comparator.comparing(function);
        Arrays.sort(data, comparator);

        setData(data);
        rerender();
    }

    /**
     * Returns the page's data
     * @return page's data
     */
    public T[] getData() {
        return data;
    }

    /**
     * Provides the page with new data
     * @param data new data to be provided to the page
     */
    public void setData(T[] data) {
        this.data = data;
    }
}
