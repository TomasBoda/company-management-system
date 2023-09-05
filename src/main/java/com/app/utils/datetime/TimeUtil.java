package com.app.utils.datetime;

import java.sql.Timestamp;

public class TimeUtil {

    /**
     * Converts a Timestamp object to a user-friendly textual representation in form of DD.MM.YYYY HH:MM:SS
     * @param timestamp Timestamp object to convert
     * @return user-friendly textual representation of the Timestamp object
     */
    public static String toString(Timestamp timestamp) {
        String[] timestampString = timestamp.toString().split(" ");

        String dateString = timestampString[0];
        String timeString = timestampString[1];

        String[] dateParts = dateString.split("-");
        String day = dateParts[2];
        String month = dateParts[1];
        String year = dateParts[0];

        String[] timeParts = timeString.split(":");
        String hours = timeParts[0];
        String minutes = timeParts[1];
        String seconds = timeParts[2].split("\\.")[0];

        return day + "." + month + "." + year + ", " + hours + ":" + minutes + ":" + seconds;
    }
}
