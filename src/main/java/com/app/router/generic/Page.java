package com.app.router.generic;

public abstract class Page<T> {

    private T data;

    /**
     * Provides the page with data and calls the onData callback
     * @param data data to provide to the page
     */
    public void setData(T data) {
        this.data = data;
        onData(data);
    }

    /**
     * Returns the page data
     * @return page data
     */
    public T getData() {
        return data;
    }

    /**
     * Callback function that is called when data is provided to the page
     * @param data provided page's data
     */
    public abstract void onData(T data);
}
