package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttSubackReturnCode;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketParseException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MqttSubackPacketTest {

    @Test
    public void testBuildAndParse() throws MqttPacketParseException {
        MqttSubackPacket packet = new MqttSubackPacket.Builder()
                .id((short) 1000)
                .addReturnCode(MqttSubackReturnCode.V3_1_1.SUCCESS0)
                .addReturnCode(MqttSubackReturnCode.V3_1_1.FAILURE)
                .build(MqttVersion.V3_1_1);

        packet.parse(MqttVersion.V3_1_1);

        Assert.assertEquals(packet.getId(), 1000);

        List<Byte> returnCodeList = packet.getReturnCodeList();
        Assert.assertEquals(returnCodeList.size(), 2);
        Assert.assertEquals((byte) returnCodeList.get(0), MqttSubackReturnCode.V3_1_1.SUCCESS0);
        Assert.assertEquals((byte) returnCodeList.get(1), MqttSubackReturnCode.V3_1_1.FAILURE);

        try {
            new MqttSubackPacket.Builder().addReturnCode((byte) 0x50);
            Assert.fail();
        } catch (Throwable e) {
            Assert.assertTrue(true);
        }

        try {
            byte[] realPacket = packet.getPacket();
            realPacket[4] = 0x50;
            new MqttPubrelPacket(realPacket, MqttVersion.V3_1_1);
            Assert.fail();
        } catch (MqttPacketParseException e) {
            Assert.assertTrue(true);
        }
    }

}