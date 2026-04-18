package com.group26.smart_home_system.event.listener;

import com.group26.smart_home_system.event.model.ActuatorMessageEvent;
import com.group26.smart_home_system.service.CommandLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLogListener {

  private final CommandLogService commandLogService;

  @Async
  @EventListener
  public void handle(ActuatorMessageEvent actuatorMessageEvent) {
    commandLogService.save(actuatorMessageEvent);
  }

}
