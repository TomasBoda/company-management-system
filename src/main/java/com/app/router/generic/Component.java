package com.app.router.generic;

public abstract class Component<T> {

    private T data;

    /**
     * Provides the component with new data
     * @param data data to be provided to the component
     */
    public void setData(T data) {
        this.data = data;
        onData(data);
    }

    /**
     * Returns the component's data
     * @return component's data
     */
    public T getData() {
        return data;
    }

    /**
     * Callback function that is called when data is provided to the component
     * @param data provided component's data
     */
    public abstract void onData(T data);
}
