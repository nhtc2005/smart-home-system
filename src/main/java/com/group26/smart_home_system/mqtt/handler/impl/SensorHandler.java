package com.group26.smart_home_system.mqtt.handler.impl;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;
import com.group26.smart_home_system.event.model.SensorDataEvent;
import com.group26.smart_home_system.mqtt.handler.MessageHandler;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SensorHandler implements MessageHandler {

  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public boolean canHandle(ParsedFeed parsedFeed) {
    return parsedFeed.getType().toLowerCase().contains("sensor");
  }

  @Override
  public void handle(ParsedFeed parsedFeed, String payload) {
    try {
      Double value = Double.parseDouble(payload);

      SensorDataEvent event = new SensorDataEvent(
          parsedFeed.getDeviceId(),
          parsedFeed.getIndex(),
          value,
          Instant.now());

      applicationEventPublisher.publishEvent(event);

      log.debug("Sensor event published: deviceId={}, value={}", parsedFeed.getDeviceId(), value);

    } catch (Exception exception) {
      log.error("Failed to handle sensor message: payload={}", payload, exception);
    }
  }

}
