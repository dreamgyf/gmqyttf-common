package com.dreamgyf.gmqyttf.common.exception.net;

import com.dreamgyf.gmqyttf.common.exception.MqttException;

public class MqttNetworkException extends MqttException {
    public MqttNetworkException() {
        super();
    }

    public MqttNetworkException(String message) {
        super(message);
    }
}
