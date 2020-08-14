package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;

public abstract class MqttPacket {

    private byte[] packet;

    protected MqttPacket() {
    }

    public MqttPacket(byte[] packet, MqttVersion version) throws MqttPacketParseException {
        this.packet = packet;
        parse(version);
    }

    protected void setPacket(byte[] packet) {
        this.packet = packet;
    }

    public byte[] getPacket() {
        return packet;
    }

    public int getLength() {
        return packet.length;
    }

    public int getRemainingLength() {
        return MqttPacketUtils.getRemainingLength(packet, 1);
    }

    protected abstract void parse(MqttVersion version) throws MqttPacketParseException;

    interface Builder {
        MqttPacket build(MqttVersion version);
    }

}
