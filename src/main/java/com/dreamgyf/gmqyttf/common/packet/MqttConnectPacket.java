package com.dreamgyf.gmqyttf.common.packet;

import com.dreamgyf.gmqyttf.common.enums.MqttPacketType;
import com.dreamgyf.gmqyttf.common.enums.MqttVersion;
import com.dreamgyf.gmqyttf.common.exception.MqttPacketParseException;
import com.dreamgyf.gmqyttf.common.utils.ByteUtils;
import com.dreamgyf.gmqyttf.common.utils.MqttPacketUtils;
import javafx.util.Pair;

import java.util.regex.Pattern;

public final class MqttConnectPacket extends MqttPacket {
    /**
     * 协议版本
     */
    private MqttVersion version;
    /**
     * 清理会话 Clean Session
     */
    private boolean cleanSession;
    /**
     * 遗嘱标志 Will Flag
     */
    private boolean willFlag;
    /**
     * 遗嘱QoS Will QoS
     */
    private int willQoS;
    /**
     * 遗嘱保留 Will Retain
     */
    private boolean willRetain;
    /**
     * 用户名标志 User Name Flag
     */
    private boolean usernameFlag;
    /**
     * 密码标志 Password Flag
     */
    private boolean passwordFlag;
    /**
     * 保持连接 Keep Alive
     */
    private short keepAliveTime;
    /**
     * 客户端标识符 Client Identifier
     */
    private String clientId;
    /**
     * 遗嘱主题 Will Topic
     */
    private String willTopic;
    /**
     * 遗嘱消息 Will Message
     */
    private String willMessage;
    /**
     * 用户名 User Name
     */
    private String username;
    /**
     * 密码 Password
     */
    private String password;

    public MqttConnectPacket(byte[] packet) throws MqttPacketParseException {
        super(packet);
    }

    private MqttConnectPacket(byte[] packet, MqttVersion version, boolean cleanSession, boolean willFlag, int willQoS,
                              boolean willRetain, boolean usernameFlag, boolean passwordFlag, short keepAliveTime,
                              String clientId, String willTopic, String willMessage, String username, String password) {
        setPacket(packet);
        this.version = version;
        this.cleanSession = cleanSession;
        this.willFlag = willFlag;
        this.willQoS = willQoS;
        this.willRetain = willRetain;
        this.usernameFlag = usernameFlag;
        this.passwordFlag = passwordFlag;
        this.keepAliveTime = keepAliveTime;
        this.clientId = clientId;
        this.willTopic = willTopic;
        this.willMessage = willMessage;
        this.username = username;
        this.password = password;
    }

    public MqttVersion getVersion() {
        return version;
    }

    public boolean isCleanSession() {
        return cleanSession;
    }

    public boolean isWillFlag() {
        return willFlag;
    }

    public int getWillQoS() {
        return willQoS;
    }

    public boolean isWillRetain() {
        return willRetain;
    }

    public boolean isUsernameFlag() {
        return usernameFlag;
    }

    public boolean isPasswordFlag() {
        return passwordFlag;
    }

    public short getKeepAliveTime() {
        return keepAliveTime;
    }

    public String getClientId() {
        return clientId;
    }

    public String getWillTopic() {
        return willTopic;
    }

    public String getWillMessage() {
        return willMessage;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    protected void parse() throws MqttPacketParseException {
        try {
            byte[] packet = getPacket();
            int pos = getLength() - getRemainingLength();
            byte[] protocolNameLength = new byte[2];
            protocolNameLength[0] = packet[pos];
            protocolNameLength[1] = packet[pos + 1];
            short protocolNameSize = ByteUtils.byte2ToShort(protocolNameLength);
            byte[] versionByte = ByteUtils.getSection(packet, pos, 2 + protocolNameSize + 1);
            this.version = MqttPacketUtils.getVersion(versionByte);
            pos += 2 + protocolNameSize + 1;
            byte connectFlags = packet[pos++];
            this.cleanSession = ByteUtils.hasBit(connectFlags, 1);
            this.willFlag = ByteUtils.hasBit(connectFlags, 2);
            if(willFlag) {
                this.willQoS = (connectFlags & 0b00011000) >> 3;
            } else {
                this.willQoS = 0;
            }
            this.willRetain = ByteUtils.hasBit(connectFlags, 5);
            this.passwordFlag = ByteUtils.hasBit(connectFlags, 6);
            this.usernameFlag = ByteUtils.hasBit(connectFlags, 7);
            byte[] keepAliveBytes = ByteUtils.getSection(packet, pos, 2);
            this.keepAliveTime = ByteUtils.byte2ToShort(keepAliveBytes);
            pos += 2;
            Pair<Integer, String> clientIdPair = MqttPacketUtils.parseUtf8EncodedStrings(packet, pos);
            this.clientId = clientIdPair.getValue();
            pos += clientIdPair.getKey();
            if (this.willFlag) {
                Pair<Integer, String> willTopicPair = MqttPacketUtils.parseUtf8EncodedStrings(packet, pos);
                this.willTopic = willTopicPair.getValue();
                pos += willTopicPair.getKey();
                Pair<Integer, String> willMessagePair = MqttPacketUtils.parseUtf8EncodedStrings(packet, pos);
                this.willMessage = willMessagePair.getValue();
                pos += willMessagePair.getKey();
            } else {
                this.willTopic = "";
                this.willMessage = "";
            }
            if (this.usernameFlag) {
                Pair<Integer, String> usernamePair = MqttPacketUtils.parseUtf8EncodedStrings(packet, pos);
                this.username = usernamePair.getValue();
                pos += usernamePair.getKey();
            } else {
                this.username = "";
            }
            if (this.passwordFlag) {
                Pair<Integer, String> passwordPair = MqttPacketUtils.parseUtf8EncodedStrings(packet, pos);
                this.password = passwordPair.getValue();
            } else {
                this.password = "";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new MqttPacketParseException("The packet is wrong!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new MqttPacketParseException("Unknown exception");
        }
    }

    @Override
    public String toString() {
        return "MqttConnectPacket{" +
                "version=" + version +
                ", cleanSession=" + cleanSession +
                ", willFlag=" + willFlag +
                ", willQoS=" + willQoS +
                ", willRetain=" + willRetain +
                ", usernameFlag=" + usernameFlag +
                ", passwordFlag=" + passwordFlag +
                ", keepAliveTime=" + keepAliveTime +
                ", clientId='" + clientId + '\'' +
                ", willTopic='" + willTopic + '\'' +
                ", willMessage='" + willMessage + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static class Builder implements MqttPacket.Builder {
        /**
         * 协议版本
         */
        private MqttVersion version = MqttVersion.V_3_1_1;
        /**
         * 清理会话 Clean Session
         */
        private boolean cleanSession;
        /**
         * 遗嘱标志 Will Flag
         */
        private boolean willFlag;
        /**
         * 遗嘱QoS Will QoS
         */
        private int willQoS = 0;
        /**
         * 遗嘱保留 Will Retain
         */
        private boolean willRetain;
        /**
         * 用户名标志 User Name Flag
         */
        private boolean usernameFlag;
        /**
         * 密码标志 Password Flag
         */
        private boolean passwordFlag;
        /**
         * 保持连接 Keep Alive
         */
        private short keepAliveTime = 10;
        /**
         * 客户端标识符 Client Identifier
         */
        private String clientId = "default";
        /**
         * 遗嘱主题 Will Topic
         */
        private String willTopic = "";
        /**
         * 遗嘱消息 Will Message
         */
        private String willMessage = "";
        /**
         * 用户名 User Name
         */
        private String username = "";
        /**
         * 密码 Password
         */
        private String password = "";

        public Builder version(MqttVersion version) {
            this.version = version;
            return this;
        }

        public Builder cleanSession(boolean cleanSession) {
            this.cleanSession = cleanSession;
            return this;
        }

        public Builder willFlag(boolean willFlag) {
            this.willFlag = willFlag;
            return this;
        }

        public Builder willQoS(int willQoS) {
            if (willQoS < 0 || willQoS > 2)
                throw new IllegalArgumentException("The value of QoS must be between 0 and 2.");
            this.willQoS = willQoS;
            return this;
        }

        public Builder willRetain(boolean willRetain) {
            this.willRetain = willRetain;
            return this;
        }

        public Builder usernameFlag(boolean usernameFlag) {
            this.usernameFlag = usernameFlag;
            return this;
        }

        public Builder passwordFlag(boolean passwordFlag) {
            this.passwordFlag = passwordFlag;
            return this;
        }

        public Builder keepAliveTime(short keepAliveTime) {
            this.keepAliveTime = keepAliveTime;
            return this;
        }

        public Builder clientId(String clientId) {
            if (!Pattern.matches("^[a-zA-Z0-9]+$", clientId))
                throw new IllegalArgumentException("illegal character,Client ID can only contain letters and Numbers");
            this.clientId = clientId;
            return this;
        }

        public Builder willTopic(String willTopic) {
            this.willTopic = willTopic;
            return this;
        }

        public Builder willMessage(String willMessage) {
            this.willMessage = willMessage;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        @Override
        public MqttConnectPacket build() {
            //构建可变报头 Variable header
            byte[] protocolName = version.getProtocolName();
            byte protocolLevel = version.getProtocolLevel();
            byte connectFlags = 0;
            if (cleanSession)
                connectFlags |= 0b00000010;
            if (willFlag) {
                connectFlags |= 0b00000100;
                connectFlags |= (willQoS << 3);
                if (willRetain)
                    connectFlags |= 0b00100000;
            }
            if (usernameFlag)
                connectFlags |= 0b10000000;
            if (passwordFlag)
                connectFlags |= 0b01000000;
            byte[] variableHeader = new byte[protocolName.length + 4]; //Protocol Name + Protocol Level + Connect Flags + Keep Alive
            int pos = 0;
            while (pos < protocolName.length) {
                variableHeader[pos] = protocolName[pos];
                pos++;
            }
            variableHeader[pos++] = protocolLevel;
            variableHeader[pos++] = connectFlags;
            byte[] keepAlive = ByteUtils.shortToByte2(keepAliveTime);
            variableHeader[pos++] = keepAlive[0];
            variableHeader[pos] = keepAlive[1];
            //构建客户端标识符 Client Identifier
            byte[] clientIdByte = MqttPacketUtils.buildUtf8EncodedStrings(clientId);
            //构建遗嘱主题 Will Topic 遗嘱消息 Will Message
            byte[] willTopicByte = new byte[0];
            byte[] willMessageByte = new byte[0];
            if (willFlag) {
                willTopicByte = MqttPacketUtils.buildUtf8EncodedStrings(willTopic);
                willMessageByte = MqttPacketUtils.buildUtf8EncodedStrings(willMessage);
            }
            //构建用户名 User Name
            byte[] usernameByte = new byte[0];
            if (usernameFlag)
                usernameByte = MqttPacketUtils.buildUtf8EncodedStrings(username);
            //构建密码 Password
            byte[] passwordByte = new byte[0];
            if (passwordFlag)
                passwordByte = MqttPacketUtils.buildUtf8EncodedStrings(password);
            //构建有效载荷 Payload
            byte[] payLoad = ByteUtils.combine(clientIdByte, willTopicByte, willMessageByte, usernameByte, passwordByte);
            //构建固定报头 Fixed header
            byte[] remainingLength = MqttPacketUtils.buildRemainingLength(variableHeader.length + payLoad.length);
            byte[] header = new byte[1];
            header[0] = MqttPacketType.CONNECT << 4;
            byte[] fixedHeader = ByteUtils.combine(header, remainingLength);
            //构建整个报文
            byte[] packet = ByteUtils.combine(fixedHeader, variableHeader, payLoad);
            return new MqttConnectPacket(packet, version, cleanSession, willFlag, willQoS, willRetain
                    , usernameFlag, passwordFlag, keepAliveTime, clientId
                    , willTopic, willMessage, username, password);
        }
    }
}
