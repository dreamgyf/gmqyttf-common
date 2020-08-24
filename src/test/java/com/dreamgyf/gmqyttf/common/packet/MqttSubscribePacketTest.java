package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.params.MqttTopic;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MqttSubscribePacketTest {

    @Test
    public void testBuildAndParse() throws MqttPacketParseException {
        MqttSubscribePacket packet = new MqttSubscribePacket.Builder()
                .id((short) 1000)
                .addTopic(new MqttTopic("test1", 0))
                .addTopic(new MqttTopic("test2", 1))
                .addTopic(new MqttTopic("test3", 2))
                .build(MqttVersion.V3_1_1);

        packet.parse(MqttVersion.V3_1_1);

        Assert.assertEquals(packet.getId(), 1000);

        List<MqttTopic> topicList = packet.getTopicList();
        Assert.assertEquals(topicList.size(), 3);

        Assert.assertEquals(topicList.get(0).getTopic(), "test1");
        Assert.assertEquals(topicList.get(1).getTopic(), "test2");
        Assert.assertEquals(topicList.get(2).getTopic(), "test3");
        Assert.assertEquals(topicList.get(0).getQoS(), 0);
        Assert.assertEquals(topicList.get(1).getQoS(), 1);
        Assert.assertEquals(topicList.get(2).getQoS(), 2);

        byte[] realPacket = packet.getPacket();

        try {
            byte[] testPacket = ByteUtils.getSection(realPacket, 0, packet.getLength() - 1);
            packet.setPacket(testPacket);
            packet.parse(MqttVersion.V3_1_1);
            Assert.assertTrue(false);
        } catch (MqttPacketParseException e) {
            Assert.assertTrue(true);
        }

        try {
            byte[] testPacket = ByteUtils.combine(realPacket, new byte[1]);
            packet.setPacket(testPacket);
            packet.parse(MqttVersion.V3_1_1);
            Assert.assertTrue(false);
        } catch (MqttPacketParseException e) {
            Assert.assertTrue(true);
        }

    }

}