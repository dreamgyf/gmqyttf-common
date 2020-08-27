package com.dreamgyf.gmqyttf.common.exception.net;

public class MqttSocketCloseException extends MqttSocketException {
    public MqttSocketCloseException() {
        super();
    }

    public MqttSocketCloseException(String message) {
        super(message);
    }
}
