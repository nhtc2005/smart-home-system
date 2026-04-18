package com.group26.smart_home_system.integration.mqtt.publisher;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MqttPublisher {

  private final MqttClient mqttClient;

  @Value("${mqtt.disconnected}")
  private String mqttDisconnected;

  @Value("${mqtt.publish.failed}")
  private String mqttPublishFailed;

  public void publish(String topic, String payload) {
    try {
      if (!mqttClient.isConnected()) {
        throw new IllegalStateException(mqttDisconnected);
      }

      MqttMessage message = new MqttMessage(payload.getBytes());
      message.setQos(1);

      mqttClient.publish(topic, message);

    } catch (Exception exception) {
      throw new RuntimeException(mqttPublishFailed, exception);
    }
  }

}
