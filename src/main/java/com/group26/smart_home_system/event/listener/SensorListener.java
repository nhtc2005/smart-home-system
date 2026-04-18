package com.group26.smart_home_system.event.listener;

import com.group26.smart_home_system.event.model.SensorDataEvent;
import com.group26.smart_home_system.service.SensorDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SensorListener {

  private final SensorDataService sensorDataService;

  @Async
  @EventListener
  public void handle(SensorDataEvent sensorDataEvent) {
    sensorDataService.save(sensorDataEvent);
  }

}
