package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import org.junit.Assert;
import org.junit.Test;

public class MqttPingreqPacketTest {

    @Test
    public void testBuildAndParse() throws MqttPacketParseException {
        MqttPingreqPacket packet = new MqttPingreqPacket.Builder()
                .build(MqttVersion.V3_1_1);

        byte[] answer = new byte[2];
        answer[0] = (byte) 0b11000000;

        Assert.assertArrayEquals(packet.getPacket(), answer);
    }

}