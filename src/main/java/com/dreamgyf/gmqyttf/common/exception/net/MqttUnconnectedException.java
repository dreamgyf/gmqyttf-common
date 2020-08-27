package com.dreamgyf.gmqyttf.common.exception.net;

public class MqttUnconnectedException extends MqttNetworkException {
    public MqttUnconnectedException() {
        super();
    }

    public MqttUnconnectedException(String message) {
        super(message);
    }
}
