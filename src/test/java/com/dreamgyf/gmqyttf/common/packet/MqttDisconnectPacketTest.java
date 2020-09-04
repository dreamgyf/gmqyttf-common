package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketParseException;
import org.junit.Assert;
import org.junit.Test;

public class MqttDisconnectPacketTest {

    @Test
    public void testBuildAndParse() throws MqttPacketParseException {
        MqttDisconnectPacket packet = new MqttDisconnectPacket.Builder()
                .build(MqttVersion.V3_1_1);

        byte[] answer = new byte[2];
        answer[0] = (byte) 0b11100000;

        Assert.assertArrayEquals(packet.getPacket(), answer);
    }
}