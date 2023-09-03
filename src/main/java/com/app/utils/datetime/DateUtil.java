package com.app.utils.datetime;

import java.sql.Date;

public class DateUtil {

    public static Date toDate(int day, int month, int year) {
        return new Date(year - 1900, month - 1, day);
    }

    public static String toString(Date date) {
        String dateString = date.toString();
        String[] parts = dateString.split("-");

        String day = parts[2];
        String month = parts[1];
        String year = parts[0];

        return day + "." + month + "." + year;
    }
}
