package com.sustainabledev.utilities.utils;

import java.util.Locale;

public class NumberUtils {

    /**
     * Give a string representing a number with a specific decimal points. Useful
     * to convert a double value to float, for example.
     * @param p an integer indicating the number decimal points
     * @param n a number to be formatted
     * @return a String format of the number
     */
    public static String formatToFloatingPoint(int p, Number n) {
        String format = "%." + p + "f";

        // Get default locale
        Locale defaultLocale = Locale.getDefault();
        Locale.setDefault(new Locale("en", "EN"));

        String result = String.format(format, n);

        // Restore the default locale to the original after getting the result
        Locale.setDefault(defaultLocale);

        return result;
    }
}
