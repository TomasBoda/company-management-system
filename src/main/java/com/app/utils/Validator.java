package com.app.utils;

public class Validator {

    public static boolean areEmpty(String... values) {
        for (String value : values) {
            if (value.trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }
}
