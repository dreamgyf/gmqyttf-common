package com.dreamgyf.gmqyttf.common.exception.packet;

import com.dreamgyf.gmqyttf.common.exception.MqttException;

public class MqttPacketException extends MqttException {
    public MqttPacketException() {
        super();
    }

    public MqttPacketException(String message) {
        super(message);
    }
}
