package com.dreamgyf.gmqyttf.common.throwable.exception.packet;

public class IllegalPacketException extends MqttPacketParseException {

    public IllegalPacketException() {
        super();
    }

    public IllegalPacketException(String message) {
        super(message);
    }
}
