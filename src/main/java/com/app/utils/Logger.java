package com.app.utils;

import com.app.api.Api;
import com.app.api.Response;
import com.app.model.Log;

public class Logger {

    /**
     * Creates a new log entry and puts it to the database
     * @param title title of the log entry
     * @param description description of the log entry
     */
    public static void log(String title, String description) {
        Response<Boolean> response = Api.logs().add(new Log(Generator.getId(), title, description));

        if (response.getStatus() != 200) {
            Dialog.info("Database Error", response.getMessage());
            System.exit(0);
        }
    }
}
