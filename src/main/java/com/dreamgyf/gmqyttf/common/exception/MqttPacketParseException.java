package com.dreamgyf.gmqyttf.common.exception;

public class MqttPacketParseException extends MqttPacketException {
    public MqttPacketParseException() {
        super();
    }

    public MqttPacketParseException(String message) {
        super(message);
    }
}
