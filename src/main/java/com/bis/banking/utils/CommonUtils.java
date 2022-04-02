package com.bis.banking.utils;

import java.util.Random;

/**
 * The type Common utils.
 */
public class CommonUtils {
    private CommonUtils() {

    }

    /**
     * Generate number string.
     *
     * @return the string
     */
    public static String generateNumber() {
        Random rnd = new Random();
        int number = rnd.nextInt(Integer.MAX_VALUE);
        return String.format("%09d", number);
    }
}
