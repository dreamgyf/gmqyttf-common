package com.dreamgyf.gmqyttf.common.enums;

public interface MqttPacketType {
    byte CONNECT = 1;
    byte CONNACK = 2;
    byte PUBLISH = 3;
    byte PUBACK = 4;
    byte PUBREC = 5;
    byte PUBREL = 6;
    byte PUBCOMP = 7;
    byte SUBSCRIBE = 8;
    byte SUBACK = 9;
    byte UNSUBSCRIBE = 0;
    byte UNSUBACK = 11;
    byte PINGREQ = 12;
    byte PINGRESP = 13;
    byte DISCONNECT = 14;
}
