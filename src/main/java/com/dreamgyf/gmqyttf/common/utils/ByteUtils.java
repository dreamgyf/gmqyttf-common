package com.dreamgyf.gmqyttf.common.utils;

public class ByteUtils {

    public static byte[] getSection(byte[] bytes, int offset, int length) {
        byte[] res = new byte[length];
        if (length >= 0) System.arraycopy(bytes, offset, res, 0, length);
        return res;
    }

    public static byte[] combine(byte[]... bytes) {
        int pos = 0;
        int len = 0;
        for (byte[] byteArray : bytes) {
            len += byteArray.length;
        }
        byte[] res = new byte[len];
        for (byte[] byteArray : bytes) {
            for (byte b : byteArray) {
                res[pos++] = b;
            }
        }
        return res;
    }

    public static byte[] shortToByte2(short n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) (n >> 8 & 0xff);
        return b;
    }

    public static short byte2ToShort(byte[] bytes) {
        int b0 = bytes[0] & 0xFF;
        int b1 = bytes[1] & 0xFF;
        return (short) ((b0 << 8) | b1);
    }

    public static boolean isEquals(byte[] bytes1, byte[] bytes2) {
        if (bytes1.length != bytes2.length) {
            return false;
        }
        for (int i = 0; i < bytes1.length; i++) {
            if (bytes1[i] != bytes2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasBit(byte b, int pos) {
        int refer = 1 << pos;
        return (b & refer) == refer;
    }
}
