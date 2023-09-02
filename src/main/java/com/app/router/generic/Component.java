package com.app.router.generic;

public abstract class Component<T> {

    private T data;

    public void setData(T data) {
        this.data = data;
        onData(data);
    }

    public T getData() {
        return data;
    }

    public abstract void onData(T data);
}
