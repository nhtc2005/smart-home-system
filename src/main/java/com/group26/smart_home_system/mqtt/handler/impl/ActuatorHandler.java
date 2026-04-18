package com.group26.smart_home_system.mqtt.handler.impl;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;
import com.group26.smart_home_system.enums.ActuatorState;
import com.group26.smart_home_system.event.model.ActuatorStateEvent;
import com.group26.smart_home_system.mqtt.handler.MessageHandler;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActuatorHandler implements MessageHandler {

  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public boolean canHandle(ParsedFeed feed) {
    return feed.getType().toLowerCase().contains("actuator");
  }

  @Override
  public void handle(ParsedFeed feed, String payload) {
    try {
      ActuatorState state = ActuatorState.valueOf(payload);

      ActuatorStateEvent event = new ActuatorStateEvent(
          feed.getDeviceId(),
          feed.getIndex(),
          state,
          Instant.now());

      applicationEventPublisher.publishEvent(event);

      log.debug("Actuator event published: deviceId={}, state={}", feed.getDeviceId(), state);

    } catch (Exception exception) {
      log.error("Failed to handle actuator message: payload={}", payload, exception);
    }
  }

}
