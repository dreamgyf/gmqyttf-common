package com.dreamgyf.gmqyttf.common.throwable.exception.packet;

public class UnknownVersionException extends MqttPacketParseException {
    public UnknownVersionException() {
        super();
    }

    public UnknownVersionException(String message) {
        super(message);
    }
}
