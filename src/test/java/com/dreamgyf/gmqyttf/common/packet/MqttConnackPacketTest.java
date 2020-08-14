package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttConnectReturnCode;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import org.junit.Test;

public class MqttConnackPacketTest {

    @Test
    public void testBuildAndParse() {
        MqttConnackPacket packet = new MqttConnackPacket.Builder()
                .sessionPresent(false)
                .connectReturnCode(MqttConnectReturnCode.ACCEPT)
                .build();

        System.out.println("设置值：" + packet);

        try {
            packet.parse();
            System.out.println("解析值：" + packet);
        } catch (MqttPacketParseException e) {
            e.printStackTrace();
            System.out.println("解析失败");
        }
    }

}