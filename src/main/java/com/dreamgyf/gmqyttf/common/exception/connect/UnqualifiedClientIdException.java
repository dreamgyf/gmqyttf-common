package com.dreamgyf.gmqyttf.common.exception.connect;

public class UnqualifiedClientIdException extends MqttConnectException {
    public UnqualifiedClientIdException() {
        super();
    }

    public UnqualifiedClientIdException(String message) {
        super(message);
    }
}
