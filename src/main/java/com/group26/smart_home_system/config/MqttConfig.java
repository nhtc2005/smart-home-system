package com.group26.smart_home_system.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

  @Value("${mqtt.broker}")
  private String brokerUrl;
  @Value("${mqtt.client}")
  private String clientId;

  @Bean
  public MqttClient mqttClient() throws MqttException {
    return new MqttClient(brokerUrl, clientId);
  }

}
