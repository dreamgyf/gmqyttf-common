package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.IllegalPacketException;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MqttUnsubscribePacket extends MqttPacket {
    /**
     * 报文标识符 Packet Identifier
     */
    private short id;
    /**
     * 主题列表 Topic List
     */
    private final List<String> topicList = new ArrayList<>();

    public MqttUnsubscribePacket(byte[] packet, MqttVersion version) throws MqttPacketParseException {
        super(packet, version);
    }

    public MqttUnsubscribePacket(byte[] packet, short id, List<String> topicList) {
        setPacket(packet);
        this.id = id;
        this.topicList.addAll(topicList);
    }

    public short getId() {
        return id;
    }

    public List<String> getTopicList() {
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
            if (MqttPacketUtils.parseType(packet[0]) != MqttPacketType.V3_1_1.UNSUBSCRIBE) {
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
                topicList.add(topic);
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
        private final List<String> topicList = new ArrayList<>();

        public Builder id(short id) {
            this.id = id;
            return this;
        }

        public Builder addTopic(String topic) {
            this.topicList.add(topic);
            return this;
        }

        public Builder addAllTopic(List<String> topicList) {
            this.topicList.addAll(topicList);
            return this;
        }

        @Override
        public MqttUnsubscribePacket build(MqttVersion version) {
            switch (version) {
                case V3_1_1:
                    return buildV311();
                default:
                    return null;
            }
        }

        private MqttUnsubscribePacket buildV311() {
            byte[] header = new byte[1];
            header[0] = (byte) (MqttPacketType.V3_1_1.UNSUBSCRIBE << 4);
            header[0] |= 0b00000010;
            byte[] variableHeader = ByteUtils.shortToByte2(id);
            byte[] payLoad = new byte[0];
            for (String topic : topicList) {
                payLoad = ByteUtils.combine(payLoad, MqttPacketUtils.buildUtf8EncodedStrings(topic));
            }
            //构建固定报头 Fixed header
            byte[] remainingLength = MqttPacketUtils.buildRemainingLength(variableHeader.length + payLoad.length);
            byte[] fixedHeader = ByteUtils.combine(header, remainingLength);
            //构建整个报文
            byte[] packet = ByteUtils.combine(fixedHeader, variableHeader, payLoad);
            return new MqttUnsubscribePacket(packet, id, topicList);
        }
    }
}
