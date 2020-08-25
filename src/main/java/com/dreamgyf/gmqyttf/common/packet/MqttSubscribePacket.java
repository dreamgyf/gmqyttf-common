package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.IllegalPacketException;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.params.MqttTopic;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MqttSubscribePacket extends MqttPacket {
    /**
     * 报文标识符 Packet Identifier
     */
    private short id;
    /**
     * 主题列表 Topic List
     */
    private final List<MqttTopic> topicList = new ArrayList<>();

    public MqttSubscribePacket(byte[] packet, MqttVersion version) throws MqttPacketParseException {
        super(packet, version);
    }

    public MqttSubscribePacket(byte[] packet, short id, List<MqttTopic> topicList) {
        setPacket(packet);
        this.id = id;
        for (MqttTopic topic : topicList) {
            MqttTopic temp = new MqttTopic(topic.getTopic(), topic.getQoS());
            this.topicList.add(temp);
        }
    }

    public short getId() {
        return id;
    }

    public List<MqttTopic> getTopicList() {
        return new ArrayList<>(topicList);
    }

    @Override
    protected void parse(MqttVersion version) throws MqttPacketParseException {
        topicList.clear();
        switch (version) {
            case V3_1_1:
                parseV311();
                break;
        }
    }

    private void parseV311() throws MqttPacketParseException {
        try {
            byte[] packet = getPacket();
            if (MqttPacketUtils.parseType(packet[0]) != MqttPacketType.V3_1_1.SUBSCRIBE) {
                throw new IllegalPacketException();
            }
            if (!ByteUtils.hasBit(packet[0], 1)) {
                throw new IllegalPacketException();
            }
            int pos = getLength() - getRemainingLength();
            byte[] idBytes = ByteUtils.getSection(packet, pos, 2);
            this.id = ByteUtils.byte2ToShort(idBytes);
            pos += 2;
            while (pos < getLength()) {
                Pair<Integer, String> topicPair = MqttPacketUtils.parseUtf8EncodedStrings(packet, pos);
                String topic = topicPair.getValue();
                pos += topicPair.getKey();
                if (packet[pos] < 0 || packet[pos] > 2)
                    throw new IllegalPacketException("The value of QoS must be between 0 and 2.");
                int QoS = packet[pos++];
                topicList.add(new MqttTopic(topic, QoS));
            }
            if (pos != getLength()) {
                throw new IllegalPacketException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new IllegalPacketException();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new MqttPacketParseException("Unknown exception");
        }
    }

    public static class Builder implements MqttPacket.Builder {
        /**
         * 报文标识符 Packet Identifier
         */
        private short id;
        /**
         * 主题列表 Topic List
         */
        private final List<MqttTopic> topicList = new ArrayList<>();

        public Builder id(short id) {
            this.id = id;
            return this;
        }

        public Builder addTopic(MqttTopic topic) {
            this.topicList.add(topic);
            return this;
        }

        public Builder addAllTopic(List<MqttTopic> topicList) {
            this.topicList.addAll(topicList);
            return this;
        }

        @Override
        public MqttSubscribePacket build(MqttVersion version) {
            switch (version) {
                case V3_1_1:
                    return buildV311();
                default:
                    return null;
            }
        }

        private MqttSubscribePacket buildV311() {
            byte[] header = new byte[1];
            header[0] = MqttPacketType.V3_1_1.SUBSCRIBE;
            header[0] <<= 4;
            header[0] |= 0b00000010;
            byte[] variableHeader = ByteUtils.shortToByte2(id);
            byte[] payLoad = new byte[0];
            for (MqttTopic topic : topicList) {
                payLoad = ByteUtils.combine(payLoad, buildTopicPacket(topic));
            }
            //构建固定报头 Fixed header
            byte[] remainingLength = MqttPacketUtils.buildRemainingLength(variableHeader.length + payLoad.length);
            byte[] fixedHeader = ByteUtils.combine(header, remainingLength);
            //构建整个报文
            byte[] packet = ByteUtils.combine(fixedHeader, variableHeader, payLoad);
            return new MqttSubscribePacket(packet, id, topicList);
        }

        private byte[] buildTopicPacket(MqttTopic mqttTopic) {
            byte[] topicByte = MqttPacketUtils.buildUtf8EncodedStrings(mqttTopic.getTopic());
            byte[] QoSByte = new byte[1];
            QoSByte[0] = (byte) mqttTopic.getQoS();
            return ByteUtils.combine(topicByte, QoSByte);
        }
    }
}
