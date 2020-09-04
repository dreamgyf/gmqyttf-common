package com.dreamgyf.gmqyttf.common.throwable.exception.connect;

import com.dreamgyf.gmqyttf.common.throwable.exception.MqttException;

public class MqttConnectException extends MqttException {
    public MqttConnectException() {
        super();
    }

    public MqttConnectException(String message) {
        super(message);
    }
}
