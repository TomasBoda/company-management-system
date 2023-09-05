package com.app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    /**
     * Checks whether the input string matches the provided regex
     * @param input input string to check
     * @param regex regex to check the input string by
     * @return a boolean indicating whether the input string matches the regex or not
     */
    public static boolean isValid(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * Checks whether the input string is in the format of an e-mail
     * @param email input string to check
     * @return a boolean indicating whether the input string is in the format of an e-mail
     */
    public static boolean isValidEmail(String email) {
        return isValid(email, EMAIL_REGEX);
    }

    /**
     * Checks whether any of the input strings is empty
     * @param values an array of input strings to check
     * @return a boolean indicating whether the array of input strings contains at least one value that is empty
     */
    public static boolean areEmpty(String... values) {
        for (String value : values) {
            if (value.trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }
}
