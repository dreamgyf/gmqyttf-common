package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import org.junit.Assert;
import org.junit.Test;


public class MqttPubcompPacketTest {

    @Test
    public void testBuildAndParse() throws MqttPacketParseException {
        MqttPubcompPacket packet = new MqttPubcompPacket.Builder()
                .id((short) 1000)
                .build(MqttVersion.V3_1_1);

        packet.parse(MqttVersion.V3_1_1);

        Assert.assertEquals(packet.getId(), 1000);
    }

}