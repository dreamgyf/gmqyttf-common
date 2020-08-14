package com.dreamgyf.gmqyttf.common.exception;

public class UnsupportedMqttVersionException extends MqttPacketParseException {
    public UnsupportedMqttVersionException() {
        super();
    }

    public UnsupportedMqttVersionException(String message) {
        super(message);
    }
}
