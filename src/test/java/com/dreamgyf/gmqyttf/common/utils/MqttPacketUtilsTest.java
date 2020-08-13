package com.dreamgyf.gmqyttf.common.utils;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import javafx.util.Pair;
import org.junit.Test;

import java.util.Arrays;

public class MqttPacketUtilsTest {

    @Test
    public void parseType() {
        byte type = MqttPacketUtils.parseType((byte) (MqttPacketType.PINGREQ << 4));
        System.out.println(type);
    }

    @Test
    public void testRemainingLength() {
        int length = 100;
        byte[] lengthBytes = MqttPacketUtils.buildRemainingLength(length);
        System.out.println("testRemainingLength bytes: " + Arrays.toString(lengthBytes));
        int lengthRes = MqttPacketUtils.getRemainingLength(lengthBytes, 0);
        System.out.println("testRemainingLength int: " + lengthRes);
    }

    @Test
    public void hasNextRemainingLength() {
        for (int i = 0x80; i <= 0xff; i++) {
            System.out.println(i + " hasNextRemainingLength: " + MqttPacketUtils.hasNextRemainingLength((byte) i));
        }
    }

    @Test
    public void testUtf8EncodedStrings() {
        String str = "MQTT是一个客户端服务端架构的发布/订阅模式的消息传输协议。" +
                "它的设计思想是轻巧、开放、简单、规范，易于实现。" +
                "这些特点使得它对很多场景来说都是很好的选择，特别是对于受限的环境如机器与机器的通信（M2M）以及物联网环境（IoT）。";
        byte[] strByte = MqttPacketUtils.buildUtf8EncodedStrings(str);
        Pair<Integer, String> res = MqttPacketUtils.parseUtf8EncodedStrings(strByte, 0);
        System.out.println("原始byte长度: " + strByte.length + " 原始String长度: " + str.length());
        System.out.println("现在byte长度: " + res.getKey() + " 现在String长度: " + res.getValue().length());
        System.out.println("正文: " + res.getValue());
    }
}