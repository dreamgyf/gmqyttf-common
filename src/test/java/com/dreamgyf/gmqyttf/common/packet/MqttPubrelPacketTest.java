package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import org.junit.Assert;
import org.junit.Test;

public class MqttPubrelPacketTest {

    @Test
    public void testBuildAndParse() throws MqttPacketParseException {
        MqttPubrelPacket packet = new MqttPubrelPacket.Builder()
                .id((short) 1000)
                .build(MqttVersion.V3_1_1);


        packet.parse(MqttVersion.V3_1_1);

        Assert.assertEquals(packet.getId(), 1000);

        try {
            byte[] realPacket = packet.getPacket();
            realPacket[0] = MqttPacketType.V3_1_1.PUBREL << 4;
            new MqttPubrelPacket(realPacket, MqttVersion.V3_1_1);
            Assert.fail();
        } catch (MqttPacketParseException e) {
            Assert.assertTrue(true);
        }
    }

}