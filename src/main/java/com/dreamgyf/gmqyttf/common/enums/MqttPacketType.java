package com.dreamgyf.gmqyttf.common.enums;

public interface MqttPacketType {
    int CONNECT = 1;
    int CONNACK = 2;
    int PUBLISH = 3;
    int PUBACK = 4;
    int PUBREC = 5;
    int PUBREL = 6;
    int PUBCOMP = 7;
    int SUBSCRIBE = 8;
    int SUBACK = 9;
    int UNSUBSCRIBE = 0;
    int UNSUBACK = 11;
    int PINGREQ = 12;
    int PINGRESP = 13;
    int DISCONNECT = 14;
}
