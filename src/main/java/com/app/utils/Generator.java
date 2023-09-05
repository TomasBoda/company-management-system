package com.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Generator {

    /**
     * Generates a hash string based on a seed
     * @param input seed of the generated hash
     * @return generated hash string
     */
    public static String getHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(input.getBytes());
            byte[] digest = md.digest();

            StringBuilder builder = new StringBuilder();
            for (byte b : digest) builder.append(String.format("%02x", b & 0xff));

            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error generating a hash");
            return null;
        }
    }

    /**
     * Generates a random hash string representing an ID
     * @return generated hash string ID
     */
    public static String getId() {
        String randomString = Long.toString(System.nanoTime());
        return getHash(randomString);
    }
}
