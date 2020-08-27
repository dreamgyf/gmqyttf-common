package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;

public final class MqttPingrespPacket extends MqttPacket {

    public MqttPingrespPacket(byte[] packet, MqttVersion version) throws MqttPacketParseException {
        super(packet, version);
    }

    public MqttPingrespPacket(byte[] packet) {
        setPacket(packet);
    }

    @Override
    protected void parse(MqttVersion version) throws MqttPacketParseException {
    }

    public static class Builder implements MqttPacket.Builder {

        @Override
        public MqttPingrespPacket build(MqttVersion version) {
            switch (version) {
                case V3_1_1:
                    return buildV311();
                default:
                    return null;
            }
        }

        private MqttPingrespPacket buildV311() {
            byte[] packet = new byte[2];
            packet[0] = (byte) (MqttPacketType.V3_1_1.PINGRESP << 4);
            return new MqttPingrespPacket(packet);
        }
    }
}
