package com.dreamgyf.gmqyttf.common.enums;

/**
 * 暂时只支持v3.1.1
 */
public enum MqttVersion {

    //    V3_1("3_1"),
    V3_1_1("3_1_1");
//    V5("5");

    private byte[] protocolName;

    private byte protocolLevel;

    MqttVersion(String version) {
        switch (version) {
            case "3_1":
                protocolName = new byte[8];
                protocolName[0] = 0;
                protocolName[1] = 6;
                protocolName[2] = "M".getBytes()[0];
                protocolName[3] = "Q".getBytes()[0];
                protocolName[4] = "I".getBytes()[0];
                protocolName[5] = "s".getBytes()[0];
                protocolName[6] = "d".getBytes()[0];
                protocolName[7] = "p".getBytes()[0];
                protocolLevel = 3;
                break;
            case "3_1_1":
                protocolName = new byte[6];
                protocolName[0] = 0;
                protocolName[1] = 4;
                protocolName[2] = "M".getBytes()[0];
                protocolName[3] = "Q".getBytes()[0];
                protocolName[4] = "T".getBytes()[0];
                protocolName[5] = "T".getBytes()[0];
                protocolLevel = 4;
                break;
            case "5":
                protocolName = new byte[6];
                protocolName[0] = 0;
                protocolName[1] = 4;
                protocolName[2] = "M".getBytes()[0];
                protocolName[3] = "Q".getBytes()[0];
                protocolName[4] = "T".getBytes()[0];
                protocolName[5] = "T".getBytes()[0];
                protocolLevel = 5;
                break;
        }
    }

    public byte[] getProtocolName() {
        return protocolName;
    }

    public byte getProtocolLevel() {
        return protocolLevel;
    }

    public byte[] getProtocolPacket() {
        byte[] res = new byte[protocolName.length + 1];
        for (int i = 0; i < protocolName.length; i++) {
            res[i] = protocolName[i];
        }
        res[res.length - 1] = protocolLevel;
        return res;
    }

}
