package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.IllegalPacketException;
import com.dreamgyf.gmqyttf.common.throwable.exception.packet.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.env.Params;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;
import javafx.util.Pair;

public final class MqttPublishPacket extends MqttPacket {
    /**
     * 如果DUP标志被设置为0，表示这是客户端或服务端第一次请求发送这个PUBLISH报文。如果DUP标志被设置为1，表示这可能是一个早前报文请求的重发。
     * 客户端或服务端请求重发一个PUBLISH报文时，必须将DUP标志设置为1 [MQTT-3.3.1.-1].。对于QoS 0的消息，DUP标志必须设置为0 [MQTT-3.3.1-2]。
     */
    private boolean DUP;
    /**
     * 服务质量等级 QoS
     */
    private int QoS;
    /**
     * 保留标志 RETAIN
     * 如果客户端发给服务端的PUBLISH报文的保留（RETAIN）标志被设置为1，
     * 服务端必须存储这个应用消息和它的服务质量等级（QoS），以便它可以被分发给未来的主题名匹配的订阅者 [MQTT-3.3.1-5]。
     * 一个新的订阅建立时，对每个匹配的主题名，如果存在最近保留的消息，它必须被发送给这个订阅者 [MQTT-3.3.1-6]。
     * <p>
     * 如果服务端收到一条保留（RETAIN）标志为1的QoS 0消息，它必须丢弃之前为那个主题保留的任何消息。
     * 它应该将这个新的QoS 0消息当作那个主题的新保留消息，但是任何时候都可以选择丢弃它 — 如果这种情况发生了，那个主题将没有保留消息 [MQTT-3.3.1-7]。
     * <p>
     * 服务端发送PUBLISH报文给客户端时，如果消息是作为客户端一个新订阅的结果发送，它必须将报文的保留标志设为1 [MQTT-3.3.1-8]。
     * 当一个PUBLISH报文发送给客户端是因为匹配一个已建立的订阅时，服务端必须将保留标志设为0，不管它收到的这个消息中保留标志的值是多少 [MQTT-3.3.1-9]。
     * <p>
     * 保留标志为1且有效载荷为零字节的PUBLISH报文会被服务端当作正常消息处理，它会被发送给订阅主题匹配的客户端。
     * 此外，同一个主题下任何现存的保留消息必须被移除，因此这个主题之后的任何订阅者都不会收到一个保留消息 [MQTT-3.3.1-10]。
     * 当作正常 意思是现存的客户端收到的消息中保留标志未被设置。服务端不能存储零字节的保留消息 [MQTT-3.3.1-11]。
     * <p>
     * 如果客户端发给服务端的PUBLISH报文的保留标志位0，服务端不能存储这个消息也不能移除或替换任何现存的保留消息 [MQTT-3.3.1-12]。
     */
    private boolean RETAIN;
    /**
     * 报文标识符 Packet Identifier
     */
    private short id;
    /**
     * 主题名 Topic Name
     */
    private String topic;
    /**
     * 将被发布的应用消息
     */
    private String message;

    public MqttPublishPacket(byte[] packet, MqttVersion version) throws MqttPacketParseException {
        super(packet, version);
    }

    public MqttPublishPacket(byte[] packet, boolean DUP, int QoS, boolean RETAIN, short id, String topic, String message) {
        setPacket(packet);
        this.DUP = DUP;
        this.QoS = QoS;
        this.RETAIN = RETAIN;
        this.id = id;
        this.topic = topic;
        this.message = message;
    }

    public boolean isDUP() {
        return DUP;
    }

    public int getQoS() {
        return QoS;
    }

    public boolean isRETAIN() {
        return RETAIN;
    }

    public short getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getMessage() {
        return message;
    }

    @Override
    protected void parse(MqttVersion version) throws MqttPacketParseException {
        switch (version) {
            case V3_1_1:
                parseV311();
                break;
        }
    }

    private void parseV311() throws MqttPacketParseException {
        try {
            byte[] packet = getPacket();
            if (MqttPacketUtils.parseType(packet[0]) != MqttPacketType.V3_1_1.PUBLISH) {
                throw new IllegalPacketException();
            }
            this.DUP = ByteUtils.hasBit(packet[0], 3);
            this.QoS = (packet[0] & 0b00000110) >> 1;
            this.RETAIN = ByteUtils.hasBit(packet[0], 0);
            int pos = getLength() - getRemainingLength();
            Pair<Integer, String> topicPair = MqttPacketUtils.parseUtf8EncodedStrings(packet, pos);
            this.topic = topicPair.getValue();
            pos += topicPair.getKey();
            if (this.QoS != 0) {
                byte[] idBytes = ByteUtils.getSection(packet, pos, 2);
                this.id = ByteUtils.byte2ToShort(idBytes);
                pos += 2;
            }
            this.message = new String(packet, pos, getLength() - pos, Params.charset);
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
         * 如果DUP标志被设置为0，表示这是客户端或服务端第一次请求发送这个PUBLISH报文。如果DUP标志被设置为1，表示这可能是一个早前报文请求的重发。
         * 客户端或服务端请求重发一个PUBLISH报文时，必须将DUP标志设置为1 [MQTT-3.3.1.-1].。对于QoS 0的消息，DUP标志必须设置为0 [MQTT-3.3.1-2]。
         */
        private boolean DUP = false;
        /**
         * 服务质量等级 QoS
         */
        private int QoS = 0;
        /**
         * 保留标志 RETAIN
         * 如果客户端发给服务端的PUBLISH报文的保留（RETAIN）标志被设置为1，
         * 服务端必须存储这个应用消息和它的服务质量等级（QoS），以便它可以被分发给未来的主题名匹配的订阅者 [MQTT-3.3.1-5]。
         * 一个新的订阅建立时，对每个匹配的主题名，如果存在最近保留的消息，它必须被发送给这个订阅者 [MQTT-3.3.1-6]。
         * <p>
         * 如果服务端收到一条保留（RETAIN）标志为1的QoS 0消息，它必须丢弃之前为那个主题保留的任何消息。
         * 它应该将这个新的QoS 0消息当作那个主题的新保留消息，但是任何时候都可以选择丢弃它 — 如果这种情况发生了，那个主题将没有保留消息 [MQTT-3.3.1-7]。
         * <p>
         * 服务端发送PUBLISH报文给客户端时，如果消息是作为客户端一个新订阅的结果发送，它必须将报文的保留标志设为1 [MQTT-3.3.1-8]。
         * 当一个PUBLISH报文发送给客户端是因为匹配一个已建立的订阅时，服务端必须将保留标志设为0，不管它收到的这个消息中保留标志的值是多少 [MQTT-3.3.1-9]。
         * <p>
         * 保留标志为1且有效载荷为零字节的PUBLISH报文会被服务端当作正常消息处理，它会被发送给订阅主题匹配的客户端。
         * 此外，同一个主题下任何现存的保留消息必须被移除，因此这个主题之后的任何订阅者都不会收到一个保留消息 [MQTT-3.3.1-10]。
         * 当作正常 意思是现存的客户端收到的消息中保留标志未被设置。服务端不能存储零字节的保留消息 [MQTT-3.3.1-11]。
         * <p>
         * 如果客户端发给服务端的PUBLISH报文的保留标志位0，服务端不能存储这个消息也不能移除或替换任何现存的保留消息 [MQTT-3.3.1-12]。
         */
        private boolean RETAIN = false;
        /**
         * 报文标识符 Packet Identifier
         */
        private short id;
        /**
         * 主题名 Topic Name
         */
        private String topic;
        /**
         * 将被发布的应用消息
         */
        private String message;

        public Builder DUP(boolean DUP) {
            this.DUP = DUP;
            return this;
        }

        public Builder QoS(int QoS) {
            if (QoS < 0 || QoS > 2)
                throw new IllegalArgumentException("The value of QoS must be between 0 and 2.");
            this.QoS = QoS;
            return this;
        }

        public Builder RETAIN(boolean RETAIN) {
            this.RETAIN = RETAIN;
            return this;
        }

        public Builder id(short id) {
            this.id = id;
            return this;
        }

        public Builder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public boolean isDUP() {
            return DUP;
        }

        public int getQoS() {
            return QoS;
        }

        public boolean isRETAIN() {
            return RETAIN;
        }

        public short getId() {
            return id;
        }

        public String getTopic() {
            return topic;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public MqttPublishPacket build(MqttVersion version) {
            switch (version) {
                case V3_1_1:
                    return buildV311();
                default:
                    return null;
            }
        }

        private MqttPublishPacket buildV311() {
            byte[] header = new byte[1];
            header[0] = MqttPacketType.V3_1_1.PUBLISH << 4;
            if (DUP)
                header[0] |= 0b00001000;
            header[0] |= QoS << 1;
            if (RETAIN)
                header[0] |= 0b00000001;
            byte[] variableHeader;
            byte[] topicByte = MqttPacketUtils.buildUtf8EncodedStrings(topic);
            //构建报文标识符 Packet Identifier
            byte[] packetIdentifier = new byte[0];
            if (QoS != 0) {
                packetIdentifier = ByteUtils.shortToByte2(id);
            }
            variableHeader = ByteUtils.combine(topicByte, packetIdentifier);
            byte[] payLoad = message.getBytes(Params.charset);
            //构建固定报头 Fixed header
            byte[] remainingLength = MqttPacketUtils.buildRemainingLength(variableHeader.length + payLoad.length);
            byte[] fixedHeader = ByteUtils.combine(header, remainingLength);
            //构建整个报文
            byte[] packet = ByteUtils.combine(fixedHeader, variableHeader, payLoad);
            return new MqttPublishPacket(packet, DUP, QoS, RETAIN, id, topic, message);
        }
    }
}
