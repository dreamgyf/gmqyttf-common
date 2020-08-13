package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import org.junit.Test;

public class MqttConnectPacketTest {

    @Test
    public void testBuildAndParse() {
        MqttConnectPacket packet = new MqttConnectPacket.Builder()
                .version(MqttVersion.V_3_1_1)
                .cleanSession(true)
                .willFlag(true)
                .willQoS(2)
                .willRetain(false)
                .usernameFlag(true)
                .passwordFlag(true)
                .username("test12345")
                .password("test12345")
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