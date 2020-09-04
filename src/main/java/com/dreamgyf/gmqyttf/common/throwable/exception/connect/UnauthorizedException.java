package com.dreamgyf.gmqyttf.common.throwable.exception.connect;

public class UnauthorizedException extends MqttConnectException {
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
