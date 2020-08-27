package com.dreamgyf.gmqyttf.common.exception.net;

public class IllegalServerException extends MqttNetworkException {
    public IllegalServerException() {
        super();
    }

    public IllegalServerException(String message) {
        super(message);
    }
}
