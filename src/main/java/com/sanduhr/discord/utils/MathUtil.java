package com.sanduhr.discord.utils;

import java.math.BigInteger;

/**
 * Created by Sanduhr on 24.07.2017 at com.sanduhr.discord.utils.
 */
public class MathUtil {
    public static String pow(int base, int expo) {
        return pow(base, expo, 10);
    }

    public static String pow(int base, int expo, int radix) {
        return new BigInteger(String.valueOf(base)).pow(expo).toString(radix);
    }
}
