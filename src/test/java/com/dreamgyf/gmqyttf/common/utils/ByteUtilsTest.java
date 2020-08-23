package com.dreamgyf.gmqyttf.common.utils;

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
        for (byte re : res) {
            System.out.println(re);
        }
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
        for (byte re : res) {
            System.out.println(re);
        }
    }

    @Test
    public void shortAndByte2() {
        short s = 128;
        byte[] bytes = ByteUtils.shortToByte2(s);
        short res = ByteUtils.byte2ToShort(bytes);
        System.out.println(res);
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
        System.out.println("应该为true: " + ByteUtils.isEquals(bytes1, bytes2));
        bytes2[5] = 1;
        System.out.println("应该为false: " + ByteUtils.isEquals(bytes1, bytes2));
    }

    @Test
    public void hasBit() {
        byte b = (byte) 0b10000000;
        System.out.println("应该为true: " + ByteUtils.hasBit(b, 7));
        System.out.println("应该为false: " + ByteUtils.hasBit(b, 5));
    }
}