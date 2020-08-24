package com.dreamgyf.gmqyttf.common.params;

public class MqttTopic {

    private String topic;

    private int QoS;

    public MqttTopic(String topic, int QoS) {
        this.topic = topic;
        if (QoS < 0 || QoS > 2)
            throw new IllegalArgumentException("The value of QoS must be between 0 and 2.");
        this.QoS = QoS;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getQoS() {
        return QoS;
    }

    public void setQoS(int qoS) {
        QoS = qoS;
    }
}
