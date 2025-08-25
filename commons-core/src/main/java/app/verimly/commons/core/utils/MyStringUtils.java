package app.verimly.commons.core.utils;

import app.verimly.commons.core.domain.exception.Assert;

public class MyStringUtils {

    /**
     * Generates a string whose length is <code>length</code>. It throws <code>IllegalArgumentException</code>
     * if length is negative. In case that length is zero, then returns empty <code>String.</code>
     *
     * @param length of the string to be generated.
     * @return an instance of <code>String</code>
     */
    public static String generateString(int length) {
        Assert.notNegative(length, "length to generate string cannot be negative");
        if (length == 0)
            return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("x");
        }
        return sb.toString();
    }
}
