package com.dreamgyf.gmqyttf.common.exception.connect;

public class UnsupportedVersionException extends MqttConnectException {
    public UnsupportedVersionException() {
        super();
    }

    public UnsupportedVersionException(String message) {
        super(message);
    }
}
