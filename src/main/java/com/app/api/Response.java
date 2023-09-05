package com.app.api;

public class Response<T> {

    private final int status;
    private final String message;
    private T data;

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * Returns the response's status code
     * @return status code of the response
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns the response's message
     * @return message of the response
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the response's data
     * @return data of the response
     */
    public T getData() {
        return data;
    }
}
