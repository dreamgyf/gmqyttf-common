package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketParseException;
import org.junit.Assert;
import org.junit.Test;

public class MqttPubackPacketTest {

    @Test
    public void testBuildAndParse() throws MqttPacketParseException {
        MqttPubackPacket packet = new MqttPubackPacket.Builder()
                .id((short) 1000)
                .build(MqttVersion.V3_1_1);

        packet.parse(MqttVersion.V3_1_1);

        Assert.assertEquals(packet.getId(), 1000);
    }

}