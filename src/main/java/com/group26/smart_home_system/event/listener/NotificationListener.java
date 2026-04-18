package com.group26.smart_home_system.event.listener;

import com.group26.smart_home_system.event.model.ActuatorStateEvent;
import com.group26.smart_home_system.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {

  private final NotificationService notificationService;

  @Async
  @EventListener
  public void handle(ActuatorStateEvent actuatorStateEvent) {
    notificationService.save(actuatorStateEvent);
  }

}
