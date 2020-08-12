package com.dreamgyf.gmqyttf.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class MqttPacketUtilsTest {

    @Test
    public void parseType() {
    }

    @Test
    public void getRemainingLength() {
    }

    @Test
    public void hasNextRemainingLength() {
        for (int i = 0x80; i <= 0xff; i++) {
            System.out.println(i + " hasNextRemainingLength: " + MqttPacketUtils.hasNextRemainingLength((byte) i));
        }
    }
}