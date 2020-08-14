package com.dreamgyf.gmqyttf.common.enums;

public interface MqttConnectReturnCode {
    /**
     * 连接已被服务端接受
     */
    byte ACCEPT = 0;
    /**
     * 服务端不支持客户端请求的MQTT协议级别
     */
    byte UNSUPPORTED_VERSION = 1;
    /**
     * 客户端标识符是正确的UTF-8编码，但服务端不允许使用
     */
    byte UNQUALIFIED_CLIENT_ID = 2;
    /**
     * 网络连接已建立，但MQTT服务不可用
     */
    byte SERVICE_UNAVAILABLE = 3;
    /**
     * 用户名或密码的数据格式无效
     */
    byte INVALID_USERNAME_OR_PASSWORD = 4;
    /**
     * 客户端未被授权连接到此服务器
     */
    byte UNAUTHORIZED = 5;
}
