package com.dreamgyf.gmqyttf.common.utils;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.packet.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.exception.packet.UnknownVersionException;
import com.dreamgyf.gmqyttf.common.env.Params;
import javafx.util.Pair;

public class MqttPacketUtils {

    public static byte parseType(byte head) {
        return (byte) ((head & 0xff) >>> 4);
    }

    public static boolean isTypeInVersion(byte type, MqttVersion version) {
        switch (version) {
            case V3_1_1: {
                return type > 0 && type < 15;
            }
        }
        return false;
    }

    public static int getRemainingLength(byte[] bytes, int start) {
        if (bytes.length - start < 1)
            return 0;
        int bits = 0;
        int pos = start;
        int length = 0;
        do {
            length += (bytes[pos] & 0x7f) << bits;
            bits += 7;
            pos++;
        } while (pos < bytes.length && (bytes[pos - 1] & 0x80) != 0);
        return length;
    }

    public static byte[] buildRemainingLength(int length) {
        byte[] res = new byte[4];
        int pos = 0;
        do {
            int digit = length % 0x80;
            length /= 0x80;
            if (length > 0 && pos < 3) {
                digit |= 0x80;
            }
            res[pos++] = (byte) digit;
        } while (length > 0 && pos < 4);
        if (length > 0) {
            throw new IllegalArgumentException("Length limit exceeded");
        }
        return ByteUtils.getSection(res, 0, pos);
    }

    public static boolean hasNextRemainingLength(byte b) {
        return ByteUtils.hasBit(b, 7);
    }

    public static byte[] buildUtf8EncodedStrings(String str) {
        byte[] strBytes = str.getBytes(Params.charset);
        if (strBytes.length > Short.MAX_VALUE) {
            throw new IllegalArgumentException("Length limit exceeded");
        }
        byte[] strBytesLength = ByteUtils.shortToByte2((short) strBytes.length);
        return ByteUtils.combine(strBytesLength, strBytes);
    }

    public static Pair<Integer, String> parseUtf8EncodedStrings(byte[] bytes, int start) {
        byte[] bytesLength = ByteUtils.getSection(bytes, start, 2);
        int length = ByteUtils.byte2ToShort(bytesLength);
        byte[] stringBytes = ByteUtils.getSection(bytes, start + 2, length);
        return new Pair<>(length + 2, new String(stringBytes, 0, length, Params.charset));
    }

    public static MqttVersion getVersion(byte[] versionByte) throws MqttPacketParseException {
        if (ByteUtils.isEquals(versionByte, MqttVersion.V3_1_1.getProtocolPacket())) {
            return MqttVersion.V3_1_1;
        } else {
            throw new UnknownVersionException("Can not parse the version!");
        }
//        if (ByteUtils.isEquals(versionByte, MqttVersion.V3_1.getProtocolPacket())) {
//            return MqttVersion.V3_1;
//        } else if (ByteUtils.isEquals(versionByte, MqttVersion.V3_1_1.getProtocolPacket())) {
//            return MqttVersion.V3_1_1;
//        } else if (ByteUtils.isEquals(versionByte, MqttVersion.V5.getProtocolPacket())) {
//            return MqttVersion.V5;
//        } else {
//            throw new UnsupportedMqttVersionException("Can not parse the version!");
//        }
    }
}
