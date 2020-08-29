package com.dreamgyf.gmqyttf.common.exception;

public class UnknownException extends MqttException {
    public UnknownException() {
        super();
    }

    public UnknownException(String message) {
        super(message);
    }
}
