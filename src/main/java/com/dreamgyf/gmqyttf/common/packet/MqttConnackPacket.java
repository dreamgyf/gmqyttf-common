package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;

public class MqttConnackPacket extends MqttPacket {
    /**
     * 当前会话 Session Present
     */
    private boolean sessionPresent;
    /**
     * 连接返回码 Connect Return code
     */
    private byte connectReturnCode;

    public MqttConnackPacket(byte[] packet) throws MqttPacketParseException {
        super(packet);
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
    protected void parse() throws MqttPacketParseException {
        try {
            byte[] packet = getPacket();
            int pos = getLength() - getRemainingLength();
            this.sessionPresent = ByteUtils.hasBit(packet[pos], 0);
            this.connectReturnCode = packet[pos + 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new MqttPacketParseException("The packet is wrong!");
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
        public MqttConnackPacket build() {
            //构建可变报头 Variable header
            byte[] variableHeader = new byte[2];
            if(sessionPresent) {
                variableHeader[0] |= 0b00000001;
            }
            variableHeader[1] = connectReturnCode;
            //构建固定报头
            byte[] header = new byte[1];
            header[0] = MqttPacketType.CONNACK << 4;
            byte[] remainingLength = MqttPacketUtils.buildRemainingLength(variableHeader.length);
            byte[] fixedHeader = ByteUtils.combine(header, remainingLength);
            //构建整个报文
            byte[] packet = ByteUtils.combine(fixedHeader, variableHeader);
            return new MqttConnackPacket(packet, sessionPresent, connectReturnCode);
        }
    }
}
