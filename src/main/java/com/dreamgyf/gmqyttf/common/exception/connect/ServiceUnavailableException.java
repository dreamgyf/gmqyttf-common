package com.dreamgyf.gmqyttf.common.exception.connect;

public class ServiceUnavailableException extends MqttConnectException {
    public ServiceUnavailableException() {
        super();
    }

    public ServiceUnavailableException(String message) {
        super(message);
    }
}
