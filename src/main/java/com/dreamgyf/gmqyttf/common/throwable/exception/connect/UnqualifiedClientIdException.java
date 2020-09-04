package com.dreamgyf.gmqyttf.common.throwable.exception.connect;

public class UnqualifiedClientIdException extends MqttConnectException {
    public UnqualifiedClientIdException() {
        super();
    }

    public UnqualifiedClientIdException(String message) {
        super(message);
    }
}
