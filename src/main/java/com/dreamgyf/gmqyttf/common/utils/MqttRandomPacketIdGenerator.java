package com.dreamgyf.gmqyttf.common.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * author: dreamgyf
 * email: g2409197994@gmail.com
 * 一个用于生成随机报文标识符的类
 * 线程同步
 */
public class MqttRandomPacketIdGenerator {

    private MqttRandomPacketIdGenerator() {
    }

    private final static Set<Short> idSet = new HashSet<>();

    public short next() {
        synchronized (this) {
            short id;
            do {
                short random = NumUtils.randomShort(true);
                id = (short) Math.max(1, random);
            } while (idSet.contains(id));
            idSet.add(id);
            return id;
        }
    }

    public boolean contains(short id) {
        synchronized (this) {
            return idSet.contains(id);
        }
    }

    public boolean remove(short id) {
        synchronized (this) {
            if (idSet.contains(id)) {
                idSet.remove(id);
                return true;
            }
            return false;
        }
    }

    public void clear() {
        synchronized (this) {
            idSet.clear();
        }
    }

    public static MqttRandomPacketIdGenerator create() {
        return new MqttRandomPacketIdGenerator();
    }
}
