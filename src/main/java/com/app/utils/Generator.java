package com.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Generator {

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

    public static String getId() {
        String randomString = Long.toString(System.nanoTime());
        return getHash(randomString);
    }
}
