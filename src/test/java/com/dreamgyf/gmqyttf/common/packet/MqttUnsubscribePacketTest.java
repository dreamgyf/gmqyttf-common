package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MqttUnsubscribePacketTest {

    @Test
    public void testBuildAndParse() throws MqttPacketParseException {
        MqttUnsubscribePacket packet = new MqttUnsubscribePacket.Builder()
                .id((short) 1000)
                .addTopic("test1")
                .addTopic("test2")
                .addTopic("test3")
                .build(MqttVersion.V3_1_1);

        packet.parse(MqttVersion.V3_1_1);

        Assert.assertEquals(packet.getId(), 1000);

        List<String> topicList = packet.getTopicList();
        Assert.assertEquals(topicList.size(), 3);
        Assert.assertEquals(topicList.get(0), "test1");
        Assert.assertEquals(topicList.get(1), "test2");
        Assert.assertEquals(topicList.get(2), "test3");

        byte[] realPacket = packet.getPacket();

        try {
            byte[] testPacket = ByteUtils.getSection(realPacket, 0, packet.getLength() - 1);
            packet.setPacket(testPacket);
            packet.parse(MqttVersion.V3_1_1);
            Assert.fail();
        } catch (MqttPacketParseException e) {
            Assert.assertTrue(true);
        }

        try {
            byte[] testPacket = ByteUtils.combine(realPacket, new byte[1]);
            packet.setPacket(testPacket);
            packet.parse(MqttVersion.V3_1_1);
            Assert.fail();
        } catch (MqttPacketParseException e) {
            Assert.assertTrue(true);
        }
    }

}