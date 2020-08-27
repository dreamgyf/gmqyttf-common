package com.dreamgyf.gmqyttf.common.exception.net;

public class MqttSocketUnconnectedException extends MqttSocketException {
    public MqttSocketUnconnectedException() {
        super();
    }

    public MqttSocketUnconnectedException(String message) {
        super(message);
    }
}
