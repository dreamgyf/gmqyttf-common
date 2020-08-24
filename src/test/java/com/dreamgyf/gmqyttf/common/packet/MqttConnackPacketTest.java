package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttConnectReturnCode;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import org.junit.Assert;
import org.junit.Test;

public class MqttConnackPacketTest {

    @Test
    public void testBuildAndParse() throws MqttPacketParseException {
        MqttConnackPacket packet = new MqttConnackPacket.Builder()
                .sessionPresent(false)
                .connectReturnCode(MqttConnectReturnCode.V3_1_1.ACCEPT)
                .build(MqttVersion.V3_1_1);

        packet.parse(MqttVersion.V3_1_1);

        Assert.assertEquals(packet.isSessionPresent(), false);
        Assert.assertEquals(packet.getConnectReturnCode(), MqttConnectReturnCode.V3_1_1.ACCEPT);
    }

}