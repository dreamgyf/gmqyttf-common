package com.dreamgyf.gmqyttf.common.enums;

public interface MqttSubackReturnCode {

    interface V3_1_1 {
        /**
         * 成功-最大QoS 0
         */
        byte SUCCESS0 = 0x00;
        /**
         * 成功-最大QoS 1
         */
        byte SUCCESS1 = 0x01;
        /**
         * 成功-最大QoS 2
         */
        byte SUCCESS2 = 0x02;
        /**
         * 失败
         */
        byte FAILURE = (byte) 0x80;
    }

}
