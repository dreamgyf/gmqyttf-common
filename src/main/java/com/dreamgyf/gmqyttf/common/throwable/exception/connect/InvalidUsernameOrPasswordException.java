package com.dreamgyf.gmqyttf.common.throwable.exception.connect;

public class InvalidUsernameOrPasswordException extends MqttConnectException {
    public InvalidUsernameOrPasswordException() {
        super();
    }

    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }
}
