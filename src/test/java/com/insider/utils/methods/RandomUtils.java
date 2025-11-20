package com.insider.utils.methods;

import java.util.Random;

/**
 * Utility class for generating random data
 * Contains methods for random strings, numbers, emails, etc.
 */
public class RandomUtils {
    private static final Random random = new Random();
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String ALPHANUMERIC = ALPHABET + NUMBERS;

    /**
     * Generate random string of specified length
     */
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    /**
     * Generate random alphanumeric string of specified length
     */
    public static String generateRandomAlphanumeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
        }
        return sb.toString();
    }

    /**
     * Generate random numeric string of specified length
     */
    public static String generateRandomNumeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        }
        return sb.toString();
    }

    /**
     * Generate random integer between min and max (inclusive)
     */
    public static int generateRandomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generate random long between min and max (inclusive)
     */
    public static long generateRandomLong(long min, long max) {
        return min + (long) (random.nextDouble() * (max - min));
    }

    /**
     * Generate random email address
     */
    public static String generateRandomEmail() {
        return generateRandomString(8).toLowerCase() + "@" + generateRandomString(5).toLowerCase() + ".com";
    }

    /**
     * Generate random email with custom domain
     */
    public static String generateRandomEmail(String domain) {
        return generateRandomString(8).toLowerCase() + "@" + domain;
    }

    /**
     * Generate random phone number (10 digits)
     */
    public static String generateRandomPhoneNumber() {
        return generateRandomNumeric(10);
    }

    /**
     * Generate random phone number with format
     */
    public static String generateRandomPhoneNumber(String format) {
        return format.replace("X", String.valueOf(random.nextInt(10)));
    }

    /**
     * Generate random boolean
     */
    public static boolean generateRandomBoolean() {
        return random.nextBoolean();
    }

    /**
     * Pick random item from array
     */
    public static <T> T pickRandom(T[] array) {
        return array[random.nextInt(array.length)];
    }
}

