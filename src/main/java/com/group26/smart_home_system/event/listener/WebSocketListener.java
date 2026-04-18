package com.group26.smart_home_system.event.listener;

import com.group26.smart_home_system.dto.websocket.ActuatorStatePayload;
import com.group26.smart_home_system.dto.websocket.SensorDataPayload;
import com.group26.smart_home_system.dto.websocket.WebSocketMessage;
import com.group26.smart_home_system.entity.Actuator;
import com.group26.smart_home_system.entity.Device;
import com.group26.smart_home_system.entity.Sensor;
import com.group26.smart_home_system.enums.WebSocketEventType;
import com.group26.smart_home_system.event.model.ActuatorStateEvent;
import com.group26.smart_home_system.event.model.SensorDataEvent;
import com.group26.smart_home_system.repository.ActuatorRepository;
import com.group26.smart_home_system.repository.DeviceRepository;
import com.group26.smart_home_system.repository.SensorRepository;
import com.group26.smart_home_system.websocket.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketListener {

  private final WebSocketService webSocketService;
  private final DeviceRepository deviceRepository;
  private final SensorRepository sensorRepository;
  private final ActuatorRepository actuatorRepository;

  @Async
  @EventListener
  public void handle(SensorDataEvent sensorDataEvent) {
    Device device = deviceRepository.findById(sensorDataEvent.getDeviceId()).orElse(null);
    Sensor sensor = sensorRepository.findById(sensorDataEvent.getSensorId()).orElse(null);

    if (device == null || sensor == null || device.getUser() == null) {
      log.warn("Skip WebSocket sensor event: deviceId={}, sensorId={}",
          sensorDataEvent.getDeviceId(),
          sensorDataEvent.getSensorId());
      return;
    }

    WebSocketMessage<SensorDataPayload> webSocketMessage = new WebSocketMessage<>();
    webSocketMessage.setType(WebSocketEventType.SENSOR_DATA);
    webSocketMessage.setTimestamp(sensorDataEvent.getTimestamp());

    SensorDataPayload sensorDataPayload = new SensorDataPayload();

    sensorDataPayload.setDeviceId(device.getId());
    sensorDataPayload.setSensorId(sensor.getId());
    sensorDataPayload.setName(sensor.getName());
    sensorDataPayload.setSensorType(sensor.getType());
    sensorDataPayload.setValue(sensorDataEvent.getValue());

    switch (sensor.getType()) {
      case LIGHT, HUMIDITY -> sensorDataPayload.setUnit("%");
      case TEMPERATURE -> sensorDataPayload.setUnit("C");
    }

    webSocketMessage.setPayload(sensorDataPayload);

    webSocketService.pushToUser(device.getUser().getId(), webSocketMessage);

    log.debug("Push sensor data to userId={}", device.getUser().getId());
  }

  @Async
  @EventListener
  public void handle(ActuatorStateEvent actuatorStateEvent) {
    Device device = deviceRepository.findById(actuatorStateEvent.getDeviceId()).orElse(null);
    Actuator actuator = actuatorRepository.findById(actuatorStateEvent.getActuatorId()).orElse(null);

    if (device == null || actuator == null || device.getUser() == null) {
      log.warn("Skip WebSocket actuator event: deviceId={}, actuatorId={}",
          actuatorStateEvent.getDeviceId(),
          actuatorStateEvent.getActuatorId());
      return;
    }

    WebSocketMessage<ActuatorStatePayload> webSocketMessage = new WebSocketMessage<>();
    webSocketMessage.setType(WebSocketEventType.ACTUATOR_STATE);
    webSocketMessage.setTimestamp(actuatorStateEvent.getTimestamp());

    ActuatorStatePayload actuatorStatePayload = new ActuatorStatePayload();

    actuatorStatePayload.setDeviceId(device.getId());
    actuatorStatePayload.setActuatorId(actuator.getId());
    actuatorStatePayload.setActuatorType(actuator.getType());
    actuatorStatePayload.setName(actuator.getName());
    actuatorStatePayload.setActuatorState(actuatorStateEvent.getActuatorState());

    webSocketMessage.setPayload(actuatorStatePayload);

    webSocketService.pushToUser(device.getUser().getId(), webSocketMessage);

    log.debug("Push actuator state to userId={}", device.getUser().getId());
  }

}
