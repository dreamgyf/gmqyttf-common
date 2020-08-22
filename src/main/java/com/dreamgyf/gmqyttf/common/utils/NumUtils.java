package com.dreamgyf.gmqyttf.common.utils;

import java.util.Random;

public class NumUtils {

    private final static Random RANDOM = new Random();

    public static short randomShort(boolean positive) {
        return (short) (positive ?
                RANDOM.nextInt(Short.MAX_VALUE) :
                RANDOM.nextInt((int) Short.MAX_VALUE - Short.MIN_VALUE) + Short.MIN_VALUE);
    }

}
