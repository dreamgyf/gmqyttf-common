package com.dreamgyf.gmqyttf.common.throwable.exception.packet;

import com.dreamgyf.gmqyttf.common.throwable.exception.MqttException;

public class MqttPacketException extends MqttException {
    public MqttPacketException() {
        super();
    }

    public MqttPacketException(String message) {
        super(message);
    }
}
