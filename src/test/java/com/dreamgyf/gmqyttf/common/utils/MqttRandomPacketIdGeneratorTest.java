package com.dreamgyf.gmqyttf.common.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class MqttRandomPacketIdGeneratorTest {

    @Test
    public void testGenerator() {
        MqttRandomPacketIdGenerator gen = MqttRandomPacketIdGenerator.create();
        Set<Short> set = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            short id = gen.next();
            Assert.assertTrue(set.add(id));
        }
        Assert.assertFalse(gen.remove((short) 0));
    }

}