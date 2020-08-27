package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.packet.IllegalPacketException;
import com.dreamgyf.gmqyttf.common.exception.packet.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;

public final class MqttConnackPacket extends MqttPacket {
    /**
     * 当前会话 Session Present
     */
    private boolean sessionPresent;
    /**
     * 连接返回码 Connect Return code
     */
    private byte connectReturnCode;

    public MqttConnackPacket(byte[] packet, MqttVersion version) throws MqttPacketParseException {
        super(packet, version);
    }

    private MqttConnackPacket(byte[] packet, boolean sessionPresent, byte connectReturnCode) {
        setPacket(packet);
        this.sessionPresent = sessionPresent;
        this.connectReturnCode = connectReturnCode;
    }

    public boolean isSessionPresent() {
        return sessionPresent;
    }

    public byte getConnectReturnCode() {
        return connectReturnCode;
    }

    @Override
    protected void parse(MqttVersion version) throws MqttPacketParseException {
        switch (version) {
            case V3_1_1:
                parseV311();
        }
    }

    private void parseV311() throws MqttPacketParseException {
        try {
            byte[] packet = getPacket();
            if (MqttPacketUtils.parseType(packet[0]) != MqttPacketType.V3_1_1.CONNACK) {
                throw new IllegalPacketException();
            }
            int pos = getLength() - getRemainingLength();
            this.sessionPresent = ByteUtils.hasBit(packet[pos++], 0);
            this.connectReturnCode = packet[pos++];
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
         * 当前会话 Session Present
         */
        private boolean sessionPresent;
        /**
         * 连接返回码 Connect Return code
         */
        private byte connectReturnCode;

        public Builder sessionPresent(boolean sessionPresent) {
            this.sessionPresent = sessionPresent;
            return this;
        }

        public Builder connectReturnCode(byte connectReturnCode) {
            this.connectReturnCode = connectReturnCode;
            return this;
        }

        @Override
        public MqttConnackPacket build(MqttVersion version) {
            switch (version) {
                case V3_1_1:
                    return buildV311();
                default:
                    return null;
            }
        }

        private MqttConnackPacket buildV311() {
            //构建可变报头 Variable header
            byte[] variableHeader = new byte[2];
            if (sessionPresent) {
                variableHeader[0] |= 0b00000001;
            }
            variableHeader[1] = connectReturnCode;
            //构建固定报头
            byte[] header = new byte[1];
            header[0] = MqttPacketType.V3_1_1.CONNACK << 4;
            byte[] remainingLength = MqttPacketUtils.buildRemainingLength(variableHeader.length);
            byte[] fixedHeader = ByteUtils.combine(header, remainingLength);
            //构建整个报文
            byte[] packet = ByteUtils.combine(fixedHeader, variableHeader);
            return new MqttConnackPacket(packet, sessionPresent, connectReturnCode);
        }
    }
}
