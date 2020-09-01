package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttSubackReturnCode;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.packet.IllegalPacketException;
import com.dreamgyf.gmqyttf.common.exception.packet.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;

import java.util.ArrayList;
import java.util.List;

public final class MqttSubackPacket extends MqttPacket {
    /**
     * 报文标识符 Packet Identifier
     */
    private short id;
    /**
     * 返回码清单
     */
    private List<Byte> returnCodeList = new ArrayList<>();

    public MqttSubackPacket(byte[] packet, MqttVersion version) throws MqttPacketParseException {
        super(packet, version);
    }

    public MqttSubackPacket(byte[] packet, short id, List<Byte> returnCodeList) {
        setPacket(packet);
        this.id = id;
        this.returnCodeList.addAll(returnCodeList);
    }

    public short getId() {
        return id;
    }

    public List<Byte> getReturnCodeList() {
        return new ArrayList<>(returnCodeList);
    }

    @Override
    protected void parse(MqttVersion version) throws MqttPacketParseException {
        if (returnCodeList == null) {
            returnCodeList = new ArrayList<>();
        }
        returnCodeList.clear();
        switch (version) {
            case V3_1_1:
                parseV311();
                break;
        }
    }

    private void parseV311() throws MqttPacketParseException {
        try {
            byte[] packet = getPacket();
            if (MqttPacketUtils.parseType(packet[0]) != MqttPacketType.V3_1_1.SUBACK) {
                throw new IllegalPacketException();
            }
            int pos = getLength() - getRemainingLength();
            byte[] idBytes = ByteUtils.getSection(packet, pos, 2);
            this.id = ByteUtils.byte2ToShort(idBytes);
            pos += 2;
            while (pos < getLength()) {
                byte code = packet[pos++];
                if (!isValidCode(code)) {
                    throw new IllegalPacketException();
                }
                this.returnCodeList.add(code);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new IllegalPacketException();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new MqttPacketParseException("Unknown exception");
        }
    }

    private static boolean isValidCode(byte code) {
        return code == MqttSubackReturnCode.V3_1_1.SUCCESS0 ||
                code == MqttSubackReturnCode.V3_1_1.SUCCESS1 ||
                code == MqttSubackReturnCode.V3_1_1.SUCCESS2 ||
                code == MqttSubackReturnCode.V3_1_1.FAILURE;
    }

    public static class Builder implements MqttPacket.Builder {
        /**
         * 报文标识符 Packet Identifier
         */
        private short id;
        /**
         * 返回码清单
         */
        private final List<Byte> returnCodeList = new ArrayList<>();

        public Builder id(short id) {
            this.id = id;
            return this;
        }

        public Builder addReturnCode(byte code) {
            if (!isValidCode(code)) {
                throw new IllegalArgumentException("Illegal Code");
            }
            this.returnCodeList.add(code);
            return this;
        }

        public Builder addAllReturnCode(List<Byte> returnCodeList) {
            for (byte code : returnCodeList) {
                if (!isValidCode(code)) {
                    throw new IllegalArgumentException("Has illegal code");
                }
                this.returnCodeList.add(code);
            }
            this.returnCodeList.addAll(returnCodeList);
            return this;
        }

        @Override
        public MqttSubackPacket build(MqttVersion version) {
            switch (version) {
                case V3_1_1:
                    return buildV311();
                default:
                    return null;
            }
        }

        private MqttSubackPacket buildV311() {
            byte[] header = new byte[1];
            header[0] = (byte) (MqttPacketType.V3_1_1.SUBACK << 4);
            byte[] idBytes = ByteUtils.shortToByte2(id);
            byte[] returnCodeBytes = new byte[returnCodeList.size()];
            for (int i = 0; i < returnCodeList.size(); i++) {
                returnCodeBytes[i] = returnCodeList.get(i);
            }
            byte[] remainLength = MqttPacketUtils.buildRemainingLength(idBytes.length + returnCodeBytes.length);
            byte[] packet = ByteUtils.combine(header, remainLength, idBytes, returnCodeBytes);
            return new MqttSubackPacket(packet, id, returnCodeList);
        }
    }
}
