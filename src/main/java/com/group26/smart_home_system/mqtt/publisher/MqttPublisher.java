package com.group26.smart_home_system.mqtt.publisher;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MqttPublisher {

  private final MqttClient mqttClient;

  @Value("${mqtt.username}")
  private String username;

  @Value("${mqtt.disconnected}")
  private String mqttDisconnected;

  @Value("${mqtt.publish.failed}")
  private String mqttPublishFailed;

  public void publish(ParsedFeed parsedFeed, String payload) {
    String topic = buildTopic(parsedFeed);

    try {
      if (!mqttClient.isConnected()) {
        log.error("MQTT publish failed: client not connected");
        throw new IllegalStateException(mqttDisconnected);
      }

      MqttMessage mqttMessage = new MqttMessage(payload.getBytes());
      mqttMessage.setQos(1);

      mqttClient.publish(topic, mqttMessage);

      log.debug("Published MQTT message to topic={}", topic);

    } catch (Exception exception) {
      log.error("MQTT publish failed: topic={}", topic, exception);
      throw new RuntimeException(mqttPublishFailed, exception);
    }
  }

  private String buildTopic(ParsedFeed parsedFeed) {
    String feed = "device_" + parsedFeed.getDeviceId() + "_actuator_" + parsedFeed.getIndex();
    return username + "/feeds/" + feed;
  }

}
