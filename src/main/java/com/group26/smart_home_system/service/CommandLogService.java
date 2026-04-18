package com.group26.smart_home_system.service;

import com.group26.smart_home_system.dto.commandlog.CommandLogResponse;
import com.group26.smart_home_system.entity.CommandLog;
import com.group26.smart_home_system.event.model.ActuatorMessageEvent;
import java.util.List;

public interface CommandLogService {

  void save(ActuatorMessageEvent actuatorMessageEvent);

  void save(CommandLog commandLog);

  List<CommandLogResponse> getCommandLogsByActuatorId(Long actuatorId);

}
