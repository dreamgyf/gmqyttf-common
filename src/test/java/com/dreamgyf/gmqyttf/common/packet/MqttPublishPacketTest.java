package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.packet.MqttPacketParseException;
import org.junit.Assert;
import org.junit.Test;

public class MqttPublishPacketTest {

    @Test
    public void testBuildAndParse() throws MqttPacketParseException {
        MqttPublishPacket packet = new MqttPublishPacket.Builder()
                .DUP(false)
                .QoS(0)
                .RETAIN(false)
                .id((short) 1000)
                .topic("/topic/test/#")
                .message("test message")
                .build(MqttVersion.V3_1_1);

        packet.parse(MqttVersion.V3_1_1);

        Assert.assertEquals(packet.isDUP(), false);
        Assert.assertEquals(packet.getQoS(), 0);
        Assert.assertEquals(packet.isRETAIN(), false);
        Assert.assertEquals(packet.getId(), 1000);
        Assert.assertEquals(packet.getTopic(), "/topic/test/#");
        Assert.assertEquals(packet.getMessage(), "test message");
    }

}