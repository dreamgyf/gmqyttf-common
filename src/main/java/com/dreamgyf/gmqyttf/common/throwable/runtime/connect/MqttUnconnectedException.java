package com.dreamgyf.gmqyttf.common.throwable.runtime.connect;

import com.dreamgyf.gmqyttf.common.throwable.runtime.net.MqttNetworkRuntimeException;

public class MqttUnconnectedException extends MqttNetworkRuntimeException {
    public MqttUnconnectedException() {
        super();
    }

    public MqttUnconnectedException(String message) {
        super(message);
    }
}
