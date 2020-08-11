package com.dreamgyf.gmqyttf.common.model;

import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;

public class MqttPacket {

    private byte[] packet;

    public MqttPacket(byte[] packet) {
        this.packet = packet;
    }

    public byte[] getPacket() {
        return packet;
    }

    public int getLength() {
        return packet.length;
    }

    public int getRemainingLength() {
        return MqttPacketUtils.getRemainingLength(packet);
    }

}
