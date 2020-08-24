package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.IllegalPacketException;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;

public final class MqttPubrelPacket extends MqttPacket {
    /**
     * 报文标识符 Packet Identifier
     */
    private short id;

    public short getId() {
        return id;
    }

    public MqttPubrelPacket(byte[] packet, MqttVersion version) throws MqttPacketParseException {
        super(packet, version);
    }

    public MqttPubrelPacket(byte[] packet, short id) {
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
        try {
            byte[] packet = getPacket();
            if (!ByteUtils.hasBit(packet[0], 1)) {
                throw new IllegalPacketException();
            }
            int pos = getLength() - getRemainingLength();
            byte[] idBytes = ByteUtils.getSection(packet, pos, 2);
            this.id = ByteUtils.byte2ToShort(idBytes);
            pos += 2;
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

        public Builder id(short id) {
            this.id = id;
            return this;
        }

        @Override
        public MqttPubrelPacket build(MqttVersion version) {
            switch (version) {
                case V3_1_1:
                    return buildV311();
                default:
                    return null;
            }
        }

        private MqttPubrelPacket buildV311() {
            byte[] header = new byte[1];
            header[0] = MqttPacketType.V3_1_1.PUBREL << 4;
            header[0] |= 0b00000010;
            byte[] idBytes = ByteUtils.shortToByte2(id);
            byte[] remainLength = MqttPacketUtils.buildRemainingLength(idBytes.length);
            byte[] packet = ByteUtils.combine(header, remainLength, idBytes);
            return new MqttPubrelPacket(packet, id);
        }
    }
}
