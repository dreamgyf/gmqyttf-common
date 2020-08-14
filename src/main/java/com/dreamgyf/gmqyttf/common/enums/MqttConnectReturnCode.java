package com.dreamgyf.gmqyttf.common.enums;

public interface MqttConnectReturnCode {

    interface V3_1_1 {
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

    interface V5 {
        /**
         * 连接已被服务端接受
         */
        byte ACCEPT = 0;
        /**
         * 服务端不愿透露的错误，或者没有适用的原因码
         */
        byte UNSPECIFIED_ERROR = (byte) 0x80;
        /**
         * CONNECT报文内容不能被正确的解析
         */
        byte INVALID_PACKET = (byte) 0x81;
        /**
         * CONNECT报文内容不符合本规范
         */
        byte PROTOCOL_ERROR = (byte) 0x82;
        /**
         * CONNECT有效，但不被服务端所接受
         */
        byte SPECIFIC_ERROR = (byte) 0x83;
        /**
         * 服务端不支持客户端请求的MQTT协议级别
         */
        byte UNSUPPORTED_VERSION = (byte) 0x84;
        /**
         * 客户端标识符是正确的UTF-8编码，但服务端不允许使用
         */
        byte UNQUALIFIED_CLIENT_ID = (byte) 0x85;
        /**
         * 客户端指定的用户名密码未被服务端所接受
         */
        byte INVALID_USERNAME_OR_PASSWORD = (byte) 0x86;
        /**
         * 客户端未被授权连接到此服务器
         */
        byte UNAUTHORIZED = (byte) 0x87;
        /**
         * 网络连接已建立，但MQTT服务不可用
         */
        byte SERVICE_UNAVAILABLE = (byte) 0x88;
    }
}
