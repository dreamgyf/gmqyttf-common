package com.dreamgyf.gmqyttf.common.exception;

public class IllegalPacketException extends MqttPacketParseException {

    public IllegalPacketException() {
        super();
    }

    public IllegalPacketException(String message) {
        super(message);
    }
}
