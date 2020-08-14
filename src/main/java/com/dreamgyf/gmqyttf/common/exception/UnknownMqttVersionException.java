package com.dreamgyf.gmqyttf.common.exception;

public class UnknownMqttVersionException extends MqttPacketParseException {
    public UnknownMqttVersionException() {
        super();
    }

    public UnknownMqttVersionException(String message) {
        super(message);
    }
}
