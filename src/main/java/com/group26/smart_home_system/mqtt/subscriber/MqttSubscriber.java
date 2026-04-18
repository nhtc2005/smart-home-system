package com.group26.smart_home_system.integration.mqtt.subscriber;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;
import com.group26.smart_home_system.integration.mqtt.dispatcher.MessageDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MqttSubscriber {

  private final MessageDispatcher dispatcher;
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
      MqttConnectOptions options = new MqttConnectOptions();
      options.setUserName(username);
      options.setPassword(key.toCharArray());
      options.setAutomaticReconnect(true);
      options.setCleanSession(false);

      mqttClient.setCallback(new MqttCallback() {

        @Override
        public void connectionLost(Throwable cause) {
          retry();
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
          handleMessage(topic, message);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
          // Not used in subscriber
        }
      });

      if (!mqttClient.isConnected()) {
        mqttClient.connect(options);
      }

      mqttClient.subscribe(topics);

    } catch (Exception e) {
      retry();
    }
  }

  private void retry() {
    new Thread(() -> {
      while (!mqttClient.isConnected()) {
        try {
          Thread.sleep(5000);
          connectAndSubscribe();
        } catch (Exception ignored) {
        }
      }
    }).start();
  }

  private void handleMessage(String topic, MqttMessage message) {
    String payload = new String(message.getPayload());

    String[] parts = topic.split("/");
    if (parts.length < 3) {
      return;
    }
    String feedKey = parts[parts.length - 1];

    ParsedFeed parsedFeed = parseFeedKey(feedKey);
    if (parsedFeed == null) {
      return;
    }
    dispatcher.dispatch(parsedFeed, payload);
  }

  private ParsedFeed parseFeedKey(String feedKey) {
    try {
      String[] parts = feedKey.split("-");

      if (parts.length < 4) return null;

      Long deviceId = Long.parseLong(parts[1]);
      String type = parts[2]; // sensor | actuator
      Long index = Long.parseLong(parts[3]);

      return new ParsedFeed(deviceId, type, index);

    } catch (Exception e) {
      return null;
    }
  }

}