package com.dreamgyf.gmqyttf.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class ByteUtilsTest {

    @Test
    public void getSection() {
        int length = 10;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) i;
        }
        byte[] res = ByteUtils.getSection(bytes, 3, 6);

        byte[] answer = new byte[6];
        for (int i = 0; i < 6; i++) {
            answer[i] = bytes[i + 3];
        }
        Assert.assertArrayEquals(res, answer);
    }

    @Test
    public void combine() {
        int length = 5;
        byte[] bytes1 = new byte[length];
        byte[] bytes2 = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes1[i] = (byte) i;
            bytes2[i] = (byte) (i + length);
        }
        byte[] res = ByteUtils.combine(bytes1, bytes2);

        byte[] answer = new byte[length * 2];
        for (int i = 0; i < length * 2; i++) {
            if (i < length) {
                answer[i] = bytes1[i];
            } else {
                answer[i] = bytes2[i - length];
            }
        }
        Assert.assertArrayEquals(res, answer);
    }

    @Test
    public void shortAndByte2() {
        short s = 128;
        byte[] bytes = ByteUtils.shortToByte2(s);
        short res = ByteUtils.byte2ToShort(bytes);
        Assert.assertEquals(s, res);
    }

    @Test
    public void isEquals() {
        int length = 10;
        byte[] bytes1 = new byte[length];
        byte[] bytes2 = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes1[i] = (byte) i;
            bytes2[i] = (byte) i;
        }
        Assert.assertTrue(ByteUtils.isEquals(bytes1, bytes2));
        bytes2[5] = 1;
        Assert.assertFalse(ByteUtils.isEquals(bytes1, bytes2));
    }

    @Test
    public void hasBit() {
        byte b = (byte) 0b10000000;
        Assert.assertTrue(ByteUtils.hasBit(b, 7));
        Assert.assertFalse(ByteUtils.hasBit(b, 5));
    }
}