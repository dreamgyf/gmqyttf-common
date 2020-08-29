package com.dreamgyf.gmqyttf.common.exception.net;

public class MqttSocketReadEmptyException extends MqttSocketReadException {
    public MqttSocketReadEmptyException() {
        super();
    }

    public MqttSocketReadEmptyException(String message) {
        super(message);
    }
}
