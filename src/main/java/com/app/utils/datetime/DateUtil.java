package com.app.utils.datetime;

import java.sql.Date;

public class DateUtil {

    /**
     * Generates a java.sql.Date object based on day, month and year
     * @param day day of the Date object
     * @param month month of the Date object
     * @param year year of the Date object
     * @return java.sql.Date object
     */
    public static Date toDate(int day, int month, int year) {
        return new Date(year - 1900, month - 1, day);
    }

    /**
     * Converts a java.sql.Date object to a user-friendly textual representation in form of DD.MM.YYYY
     * @param date java.sql.Date object to convert
     * @return user-friendly textual representation of the java.sql.Date object
     */
    public static String toString(Date date) {
        String dateString = date.toString();
        String[] parts = dateString.split("-");

        String day = parts[2];
        String month = parts[1];
        String year = parts[0];

        return day + "." + month + "." + year;
    }
}
