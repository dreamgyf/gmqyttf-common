package com.dreamgyf.gmqyttf.common.throwable.exception.net;

import com.dreamgyf.gmqyttf.common.throwable.exception.MqttException;

public class MqttNetworkException extends MqttException {
    public MqttNetworkException() {
        super();
    }

    public MqttNetworkException(String message) {
        super(message);
    }
}
