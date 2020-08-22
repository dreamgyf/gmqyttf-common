package com.dreamgyf.gmqyttf.common.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class NumUtilsTest {

    /**
     * 测试原则：将random区间分段，记录每段随机数产生的个数，
     * 当最大值和最小值之差不大于10%时则认为这个随机函数没有问题
     */
    @Test
    public void randomShort() {
        int testCount = 10000;

        int positionRange = Short.MAX_VALUE;
        int positionPartCount = 100;
        double positionPartNumDouble = (double) positionRange / positionPartCount;
        int positionPartNum = (int) ((positionPartNumDouble - (int) positionPartNumDouble) > 0 ? positionPartNumDouble + 1 : positionPartNumDouble);
        int[] positionCountRecorder = new int[positionPartCount];

        int allRange = Short.MAX_VALUE - Short.MIN_VALUE;
        int allPartCount = 200;
        double allPartNumDouble = (double) allRange / allPartCount;
        int allPartNum = (int) ((allPartNumDouble - (int) allPartNumDouble) > 0 ? allPartNumDouble + 1 : allPartNumDouble);
        int[] allCountRecord = new int[allPartCount];

        for (int i = 0; i < testCount; i++) {
            short randomPositive = NumUtils.randomShort(true);
            positionCountRecorder[randomPositive / positionPartNum]++;

            short randomAll = NumUtils.randomShort(false);
            allCountRecord[(randomAll - Short.MIN_VALUE) / allPartNum]++;
        }

        int positiveMax = getMax(positionCountRecorder);
        int positiveMin = getMin(positionCountRecorder);
        Assert.assertTrue(positiveMax - positiveMin < testCount * 0.1);

        int allMax = getMax(allCountRecord);
        int allMin = getMin(allCountRecord);
        Assert.assertTrue(allMax - allMin < testCount * 0.1);
    }

    private int getMax(int[] array) {
        int max = Short.MIN_VALUE;
        for (int i : array) {
            max = Math.max(max, i);
        }
        return max;
    }

    private int getMin(int[] array) {
        int min = Short.MAX_VALUE;
        for (int i : array) {
            min = Math.min(min, i);
        }
        return min;
    }

}