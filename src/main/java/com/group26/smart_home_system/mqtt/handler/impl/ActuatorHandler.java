package com.group26.smart_home_system.integration.mqtt.handler.impl;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;
import com.group26.smart_home_system.dto.mqtt.SensorMessage;
import com.group26.smart_home_system.integration.mqtt.handler.MessageHandler;
import com.group26.smart_home_system.service.TelemetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActuatorHandler implements MessageHandler {

  private final TelemetryService telemetryService;

  @Override
  public boolean canHandle(ParsedFeed feed) {
    return feed.getType().toLowerCase().contains("actuator");
  }

  @Override
  public void handle(ParsedFeed feed, String payload) {
    SensorMessage message = new SensorMessage();
    System.out.println("Actuator " + feed.getIndex() + ": " + message.getValue());
    telemetryService.handle(message);
  }

}
