package com.dreamgyf.gmqyttf.common.throwable.runtime.connect;

import com.dreamgyf.gmqyttf.common.throwable.runtime.net.MqttNetworkRuntimeException;

public class MqttConnectedException extends MqttNetworkRuntimeException {
    public MqttConnectedException() {
        super();
    }

    public MqttConnectedException(String message) {
        super(message);
    }
}
