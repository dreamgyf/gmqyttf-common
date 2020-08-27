package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;

public final class MqttPingreqPacket extends MqttPacket {

    public MqttPingreqPacket(byte[] packet, MqttVersion version) throws MqttPacketParseException {
        super(packet, version);
    }

    public MqttPingreqPacket(byte[] packet) {
        setPacket(packet);
    }

    @Override
    protected void parse(MqttVersion version) throws MqttPacketParseException {
    }

    public static class Builder implements MqttPacket.Builder {

        @Override
        public MqttPingreqPacket build(MqttVersion version) {
            switch (version) {
                case V3_1_1:
                    return buildV311();
                default:
                    return null;
            }
        }

        private MqttPingreqPacket buildV311() {
            byte[] packet = new byte[2];
            packet[0] = (byte) (MqttPacketType.V3_1_1.PINGREQ << 4);
            return new MqttPingreqPacket(packet);
        }
    }
}
