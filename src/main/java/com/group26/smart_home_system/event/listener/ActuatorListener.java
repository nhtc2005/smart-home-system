package com.group26.smart_home_system.event.listener;

import com.group26.smart_home_system.dto.mqtt.ParsedFeed;
import com.group26.smart_home_system.event.model.ActuatorMessageEvent;
import com.group26.smart_home_system.event.model.ActuatorStateEvent;
import com.group26.smart_home_system.mqtt.publisher.MqttPublisher;
import com.group26.smart_home_system.service.ActuatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActuatorListener {

  private final MqttPublisher mqttPublisher;
  private final ActuatorService actuatorService;

  @Async
  @EventListener
  public void handle(ActuatorStateEvent actuatorStateEvent) {
    actuatorService.setActuatorState(actuatorStateEvent);
  }

  @Async
  @EventListener
  public void handle(ActuatorMessageEvent actuatorMessageEvent) {
    log.debug("Publishing actuator command: deviceId={}, actuatorId={}",
        actuatorMessageEvent.getDeviceId(),
        actuatorMessageEvent.getActuatorId());

    ParsedFeed parsedFeed = new ParsedFeed(
        actuatorMessageEvent.getDeviceId(),
        "actuator",
        actuatorMessageEvent.getActuatorId());
    mqttPublisher.publish(parsedFeed, actuatorMessageEvent.getMessage());
  }

}
