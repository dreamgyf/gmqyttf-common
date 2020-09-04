package com.dreamgyf.gmqyttf.common.utils;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketParseException;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

public class MqttPacketUtilsTest {

    @Test
    public void parseType() {
        byte type = MqttPacketUtils.parseType((byte) (MqttPacketType.V3_1_1.PINGREQ << 4));
        Assert.assertEquals(type, MqttPacketType.V3_1_1.PINGREQ);
    }

    @Test
    public void isTypeInVersion() {
        Assert.assertTrue(MqttPacketUtils.isTypeInVersion((byte) 11, MqttVersion.V3_1_1));
        Assert.assertFalse(MqttPacketUtils.isTypeInVersion((byte) 0, MqttVersion.V3_1_1));
    }

    @Test
    public void testRemainingLength() {
        int length = 100;
        byte[] lengthBytes = MqttPacketUtils.buildRemainingLength(length);
        int lengthRes = MqttPacketUtils.getRemainingLength(lengthBytes, 0);
        Assert.assertEquals(length, lengthRes);
    }

    @Test
    public void hasNextRemainingLength() {
        Assert.assertTrue(MqttPacketUtils.hasNextRemainingLength((byte) 0b10000000));
        Assert.assertFalse(MqttPacketUtils.hasNextRemainingLength((byte) 0b00000000));
    }

    @Test
    public void testUtf8EncodedStrings() {
        String str = "MQTT是一个客户端服务端架构的发布/订阅模式的消息传输协议。" +
                "它的设计思想是轻巧、开放、简单、规范，易于实现。" +
                "这些特点使得它对很多场景来说都是很好的选择，特别是对于受限的环境如机器与机器的通信（M2M）以及物联网环境（IoT）。";
        byte[] strByte = MqttPacketUtils.buildUtf8EncodedStrings(str);
        Pair<Integer, String> res = MqttPacketUtils.parseUtf8EncodedStrings(strByte, 0);

        Assert.assertEquals(strByte.length, res.getKey().intValue());
        Assert.assertEquals(str.length(), res.getValue().length());
        Assert.assertEquals(str, res.getValue());
    }

    @Test
    public void getVersion() {
        byte[] versionByte = MqttVersion.V3_1_1.getProtocolPacket();
        try {
            Assert.assertEquals(MqttPacketUtils.getVersion(versionByte), MqttVersion.V3_1_1);
        } catch (MqttPacketParseException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
        versionByte[0] = (byte) 0xff;
        try {
            Assert.assertNotEquals(MqttPacketUtils.getVersion(versionByte), MqttVersion.V3_1_1);
        } catch (MqttPacketParseException e) {
            Assert.assertTrue(true);
        }
    }

}