package com.group26.smart_home_system.mqtt.subscriber;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;
import com.group26.smart_home_system.mqtt.dispatcher.MessageDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MqttSubscriber {

  private final MessageDispatcher messageDispatcher;
  private final MqttClient mqttClient;

  @Value("${mqtt.username}")
  private String username;

  @Value("${mqtt.key}")
  private String key;

  @Value("${mqtt.topics}")
  private String topics;

  @PostConstruct
  public void init() {
    connectAndSubscribe();
  }

  private void connectAndSubscribe() {
    try {
      MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
      mqttConnectOptions.setUserName(username);
      mqttConnectOptions.setPassword(key.toCharArray());
      mqttConnectOptions.setAutomaticReconnect(true);
      mqttConnectOptions.setCleanSession(false);

      mqttClient.setCallback(new MqttCallback() {

        @Override
        public void connectionLost(Throwable cause) {
          log.warn("MQTT connection lost", cause);
          retry();
        }

        @Override
        public void messageArrived(String topic, MqttMessage mqttMessage) {
          handleMessage(topic, mqttMessage);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
          // Not used in subscriber
        }
      });

      if (!mqttClient.isConnected()) {
        mqttClient.connect(mqttConnectOptions);
        log.info("MQTT connected");
      }

      mqttClient.subscribe(topics);
      log.info("MQTT subscribed to topics={}", topics);

    } catch (Exception exception) {
      log.error("MQTT connect/subscribe failed", exception);
      retry();
    }
  }

  private void retry() {
    new Thread(() -> {
      log.warn("Starting MQTT reconnect loop...");

      while (!mqttClient.isConnected()) {
        try {
          Thread.sleep(5000);
          connectAndSubscribe();
        } catch (Exception exception) {
          log.error("MQTT reconnect attempt failed", exception);
        }
      }

      log.info("MQTT reconnected successfully");
    }).start();
  }

  private void handleMessage(String topic, MqttMessage mqttMessage) {
    try {
      String payload = new String(mqttMessage.getPayload());

      String[] parts = topic.split("/");
      if (parts.length < 3) {
        log.warn("Invalid MQTT topic format: {}", topic);
        return;
      }
      String feedKey = parts[parts.length - 1];

      ParsedFeed parsedFeed = parseFeedKey(feedKey);
      if (parsedFeed == null) {
        log.warn("Invalid feed key: {}", feedKey);
        return;
      }

      messageDispatcher.dispatch(parsedFeed, payload);
    } catch (Exception exception) {
      log.error("Failed to handle MQTT message: topic={}", topic, exception);
    }
  }

  private ParsedFeed parseFeedKey(String feedKey) {
    try {
      String[] parts = feedKey.split("-");

      if (parts.length < 4) return null;

      Long deviceId = Long.parseLong(parts[1]);
      String type = parts[2];
      Long index = Long.parseLong(parts[3]);

      return new ParsedFeed(deviceId, type, index);

    } catch (Exception exception) {
      log.warn("Failed to parse feedKey={}", feedKey);
      return null;
    }
  }

}
