package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;

public class MqttPubackPacket extends MqttPacket {
    /**
     * 报文标识符 Packet Identifier
     */
    private short id;

    public short getId() {
        return id;
    }

    public MqttPubackPacket(byte[] packet, MqttVersion version) throws MqttPacketParseException {
        super(packet, version);
    }

    public MqttPubackPacket(byte[] packet, short id) {
        setPacket(packet);
        this.id = id;
    }

    @Override
    protected void parse(MqttVersion version) throws MqttPacketParseException {
        switch (version) {
            case V3_1_1:
                parseV311();
        }
    }

    private void parseV311() throws MqttPacketParseException {
        int pos;
        try {
            byte[] packet = getPacket();
            pos = getLength() - getRemainingLength();
            byte[] idBytes = ByteUtils.getSection(packet, pos, 2);
            this.id = ByteUtils.byte2ToShort(idBytes);
            pos += 2;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new MqttPacketParseException("The packet is wrong!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new MqttPacketParseException("Unknown exception");
        }
        if (pos != getLength()) {
            throw new MqttPacketParseException("The packet is wrong!");
        }
    }

    public static class Builder implements MqttPacket.Builder {
        /**
         * 报文标识符 Packet Identifier
         */
        private short id;

        public Builder id(short id) {
            this.id = id;
            return this;
        }

        @Override
        public MqttPubackPacket build(MqttVersion version) {
            switch (version) {
                case V3_1_1:
                    return buildV311();
                default:
                    return null;
            }
        }

        private MqttPubackPacket buildV311() {
            byte[] header = new byte[1];
            header[0] = MqttPacketType.V3_1_1.PUBACK << 4;
            byte[] idBytes = ByteUtils.shortToByte2(id);
            byte[] remainLength = MqttPacketUtils.buildRemainingLength(idBytes.length);
            byte[] packet = ByteUtils.combine(header, remainLength, idBytes);
            return new MqttPubackPacket(packet, id);
        }
    }
}
