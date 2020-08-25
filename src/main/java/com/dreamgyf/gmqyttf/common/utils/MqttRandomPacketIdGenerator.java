package com.dreamgyf.gmqyttf.common.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * author: dreamgyf
 * email: g2409197994@gmail.com
 * 一个用于生成随机报文标识符的类
 * 暂不考虑异步操作
 */
public class MqttRandomPacketIdGenerator {

    private MqttRandomPacketIdGenerator() {
    }

    private final static Set<Short> idSet = new HashSet<>();

    public short next() {
        short id;
        do {
            short random = NumUtils.randomShort(true);
            id = (short) Math.max(1, random);
        } while (idSet.contains(id));
        idSet.add(id);
        return id;
    }

    public boolean remove(short id) {
        if (idSet.contains(id)) {
            idSet.remove(id);
            return true;
        }
        return false;
    }

    public static MqttRandomPacketIdGenerator create() {
        return new MqttRandomPacketIdGenerator();
    }
}
