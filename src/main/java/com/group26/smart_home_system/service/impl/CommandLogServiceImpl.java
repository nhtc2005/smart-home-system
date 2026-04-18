package com.group26.smart_home_system.service.impl;

import com.group26.smart_home_system.dto.commandlog.CommandLogResponse;
import com.group26.smart_home_system.entity.Actuator;
import com.group26.smart_home_system.entity.CommandLog;
import com.group26.smart_home_system.enums.CommandSource;
import com.group26.smart_home_system.enums.CommandStatus;
import com.group26.smart_home_system.event.model.ActuatorMessageEvent;
import com.group26.smart_home_system.repository.ActuatorRepository;
import com.group26.smart_home_system.repository.CommandLogRepository;
import com.group26.smart_home_system.mapper.CommandLogMapper;
import com.group26.smart_home_system.service.CommandLogService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandLogServiceImpl implements CommandLogService {

  private final CommandLogRepository commandLogRepository;
  private final ActuatorRepository actuatorRepository;
  private final CommandLogMapper commandLogMapper;

  @Override
  @Transactional
  public void save(ActuatorMessageEvent actuatorMessageEvent) {
    Actuator actuator = actuatorRepository.findById(actuatorMessageEvent.getActuatorId()).orElse(null);
    if (actuator == null) {
      return;
    }
    CommandLog commandLog = new CommandLog();
    commandLog.setActuator(actuator);
    commandLog.setCommand(actuatorMessageEvent.getMessage());
    commandLog.setTimestamp(actuatorMessageEvent.getTimestamp());
    commandLog.setSource(CommandSource.SYSTEM);
    commandLog.setStatus(CommandStatus.SENT);
    commandLogRepository.save(commandLog);
  }

  @Override
  @Transactional
  public void save(CommandLog commandLog) {
    commandLogRepository.save(commandLog);
  }

  @Override
  public List<CommandLogResponse> getCommandLogsByActuatorId(Long actuatorId) {
    return commandLogMapper.toResponseList(commandLogRepository.findByActuatorId(actuatorId));
  }

}
