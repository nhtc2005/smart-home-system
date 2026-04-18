package com.group26.smart_home_system.integration.mqtt.handler.impl;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;
import com.group26.smart_home_system.dto.mqtt.SensorMessage;
import com.group26.smart_home_system.integration.mqtt.handler.MessageHandler;
import com.group26.smart_home_system.service.TelemetryService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SensorHandler implements MessageHandler {

  private final TelemetryService telemetryService;

  @Override
  public boolean canHandle(ParsedFeed feed) {
    return feed.getType().toLowerCase().contains("sensor");
  }

  @Override
  public void handle(ParsedFeed feed, String payload) {
    Double value = Double.parseDouble(payload);
    SensorMessage message = new SensorMessage(feed.getDeviceId(), feed.getIndex(), value,
        LocalDateTime.now());
    System.out.println("Sensor " + feed.getIndex() + ": " + message.getValue());
    telemetryService.handle(message);
  }

}
