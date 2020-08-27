package com.dreamgyf.gmqyttf.common.exception.net;

public class MqttConnectedException extends MqttNetworkException {
    public MqttConnectedException() {
        super();
    }

    public MqttConnectedException(String message) {
        super(message);
    }
}
