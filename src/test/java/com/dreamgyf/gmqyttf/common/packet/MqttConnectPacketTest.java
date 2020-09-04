package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketParseException;
import org.junit.Assert;
import org.junit.Test;

public class MqttConnectPacketTest {

    @Test
    public void testBuildAndParse() throws MqttPacketParseException {
        MqttConnectPacket packet = new MqttConnectPacket.Builder()
                .cleanSession(true)
                .willFlag(true)
                .willQoS(2)
                .willRetain(false)
                .usernameFlag(true)
                .passwordFlag(true)
                .username("test12345")
                .password("test12345")
                .keepAliveTime((short) 10)
                .clientId("test")
                .willTopic("/topic/test/#")
                .willMessage("test message")
                .build(MqttVersion.V3_1_1);

        packet.parse(null);

        Assert.assertEquals(packet.isCleanSession(), true);
        Assert.assertEquals(packet.isWillFlag(), true);
        Assert.assertEquals(packet.getWillQoS(), 2);
        Assert.assertEquals(packet.isWillRetain(), false);
        Assert.assertEquals(packet.isUsernameFlag(), true);
        Assert.assertEquals(packet.isPasswordFlag(), true);
        Assert.assertEquals(packet.getUsername(), "test12345");
        Assert.assertEquals(packet.getPassword(), "test12345");
        Assert.assertEquals(packet.getKeepAliveTime(), 10);
        Assert.assertEquals(packet.getClientId(), "test");
        Assert.assertEquals(packet.getWillTopic(), "/topic/test/#");
        Assert.assertEquals(packet.getWillMessage(), "test message");
    }
}