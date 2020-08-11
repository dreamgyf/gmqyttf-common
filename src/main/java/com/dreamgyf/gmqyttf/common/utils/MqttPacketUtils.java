package com.dreamgyf.gmqyttf.common.utils;

public class MqttPacketUtils {

    public static int getRemainingLength(byte[] packet) {
        if(packet.length < 2)
            return 0;
        int bits = 0;
        int pos = 1;
        int length = 0;
        do {
            length += (packet[pos] & 0x7f) << bits;
            bits += 7;
            pos++;
        } while(pos < packet.length && (packet[pos - 1] & 0x80) != 0);
        return length;
    }
}
