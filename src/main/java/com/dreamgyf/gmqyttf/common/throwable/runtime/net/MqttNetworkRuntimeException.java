package com.dreamgyf.gmqyttf.common.throwable.runtime.net;

import com.dreamgyf.gmqyttf.common.throwable.runtime.MqttRuntimeException;

public class MqttNetworkRuntimeException extends MqttRuntimeException {
    public MqttNetworkRuntimeException() {
        super();
    }

    public MqttNetworkRuntimeException(String message) {
        super(message);
    }
}
