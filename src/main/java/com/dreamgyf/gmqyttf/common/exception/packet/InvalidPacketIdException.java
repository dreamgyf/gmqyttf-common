package com.dreamgyf.gmqyttf.common.exception.packet;

public class InvalidPacketIdException extends MqttPacketException {
    public InvalidPacketIdException() {
        super();
    }

    public InvalidPacketIdException(String message) {
        super(message);
    }
}
