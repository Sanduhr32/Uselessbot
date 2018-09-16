package com.sanduhr.discord.utils;

import java.math.BigInteger;

/**
 * Created by Sanduhr on 24.07.2017 at com.sanduhr.discord.utils.
 */
public class MathUtil {
    public static BigInteger factorize(int start) {
        BigInteger bint = BigInteger.valueOf(start);
        while (start > 1) {
            start--;
            bint = bint.multiply(BigInteger.valueOf(start));
        }
        return bint;
    }
}
