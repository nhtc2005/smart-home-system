package com.group26.smart_home_system.service.impl;

import com.group26.smart_home_system.dto.schedule.CreateScheduleRequest;
import com.group26.smart_home_system.dto.schedule.ScheduleResponse;
import com.group26.smart_home_system.entity.Actuator;
import com.group26.smart_home_system.entity.Schedule;
import com.group26.smart_home_system.exception.ActuatorNotFoundException;
import com.group26.smart_home_system.exception.ScheduleNotFoundException;
import com.group26.smart_home_system.mapper.ScheduleMapper;
import com.group26.smart_home_system.repository.ActuatorRepository;
import com.group26.smart_home_system.repository.ScheduleRepository;
import com.group26.smart_home_system.service.DynamicScheduleService;
import com.group26.smart_home_system.service.ScheduleService;
import com.group26.smart_home_system.utils.CronUtils;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

  private final DynamicScheduleService dynamicScheduleService;
  private final ScheduleRepository scheduleRepository;
  private final ActuatorRepository actuatorRepository;
  private final ScheduleMapper scheduleMapper;

  @Value("${actuator.not.found}")
  private String actuatorNotFound;

  @Value("${schedule.not.found}")
  private String scheduleNotFound;

  @Override
  @Transactional
  public ScheduleResponse createSchedule(CreateScheduleRequest createScheduleRequest)
      throws ActuatorNotFoundException {
    Schedule schedule = scheduleMapper.toEntity(createScheduleRequest);

    String cron = CronUtils.buildCron(
        createScheduleRequest.getTime(),
        createScheduleRequest.getDays(),
        createScheduleRequest.getMode()
    );

    Actuator actuator = actuatorRepository.findById(createScheduleRequest.getActuatorId())
        .orElseThrow(() -> new ActuatorNotFoundException(actuatorNotFound));

    schedule.setCronExpression(cron);
    schedule.setActuator(actuator);
    schedule.setUser(actuator.getDevice().getUser());

    scheduleRepository.save(schedule);

    dynamicScheduleService.schedule(schedule);

    return scheduleMapper.toResponse(schedule);
  }

  @Override
  public List<ScheduleResponse> getScheduleByActuatorId(Long actuatorId)
      throws ActuatorNotFoundException {
    Actuator actuator = actuatorRepository.findById(actuatorId)
        .orElseThrow(() -> new ActuatorNotFoundException(actuatorNotFound));
    return scheduleMapper.toResponseList(scheduleRepository.findByActuatorId(actuator.getId()));
  }

  @Override
  @Transactional
  public void deleteSchedule(Long scheduleId) throws ScheduleNotFoundException {
    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow(() -> new ScheduleNotFoundException(scheduleNotFound));
    scheduleRepository.delete(schedule);
  }

}
