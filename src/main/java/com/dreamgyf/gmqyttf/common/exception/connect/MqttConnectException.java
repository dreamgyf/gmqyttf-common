package com.dreamgyf.gmqyttf.common.exception.connect;

import com.dreamgyf.gmqyttf.common.exception.MqttException;

public class MqttConnectException extends MqttException {
    public MqttConnectException() {
        super();
    }

    public MqttConnectException(String message) {
        super(message);
    }
}
