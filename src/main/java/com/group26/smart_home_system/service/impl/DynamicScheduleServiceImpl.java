package com.group26.smart_home_system.service.impl;

import com.group26.smart_home_system.entity.Actuator;
import com.group26.smart_home_system.entity.Schedule;
import com.group26.smart_home_system.enums.ActuatorMode;
import com.group26.smart_home_system.enums.ScheduleMode;
import com.group26.smart_home_system.event.model.ActuatorMessageEvent;
import com.group26.smart_home_system.repository.ActuatorRepository;
import com.group26.smart_home_system.repository.ScheduleRepository;
import com.group26.smart_home_system.service.DynamicScheduleService;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DynamicScheduleServiceImpl implements DynamicScheduleService {

  private final TaskScheduler taskScheduler;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final ActuatorRepository actuatorRepository;
  private final ScheduleRepository scheduleRepository;

  private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

  @Override
  public void schedule(Schedule schedule) {

    cancel(schedule.getId());

    Runnable task = () -> {
      try {
        executeSchedule(schedule.getId());
      } catch (Exception exception) {
        log.error("Error executing schedule {}", schedule.getId(), exception);
      }
    };

    ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(
        task,
        new CronTrigger(schedule.getCronExpression()));

    scheduledTasks.put(schedule.getId(), scheduledFuture);

    log.info("Scheduled job created for scheduleId={}", schedule.getId());
  }

  private void executeSchedule(Long scheduleId) {
    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElse(null);

    if (schedule == null) return;

    Actuator actuator = actuatorRepository
        .findById(schedule.getActuator().getId())
        .orElse(null);

    if (actuator == null) return;

    if (actuator.getMode() != ActuatorMode.SCHEDULE) {
      log.debug("Skip schedule {} because actuator not in SCHEDULE mode", scheduleId);
      return;
    }

    ActuatorMessageEvent event = ActuatorMessageEvent.builder()
        .deviceId(actuator.getDevice().getId())
        .actuatorId(actuator.getId())
        .message(schedule.getAction().name())
        .timestamp(Instant.now())
        .build();

    applicationEventPublisher.publishEvent(event);

    schedule.setLastExecutedAt(Instant.now());
    scheduleRepository.save(schedule);

    if (schedule.getMode() == ScheduleMode.ONCE) {
      cancel(scheduleId);
      scheduleRepository.delete(schedule);
    }

    log.info("Executed schedule {}", scheduleId);
  }

  @Override
  public void cancel(Long scheduleId) {
    ScheduledFuture<?> future = scheduledTasks.remove(scheduleId);
    if (future != null) {
      future.cancel(true);
      log.info("Cancelled schedule {}", scheduleId);
    }
  }

  @Override
  public void reschedule(Schedule schedule) {
    schedule(schedule);
  }

  @Override
  @PostConstruct
  public void init() {
    List<Schedule> schedules = scheduleRepository.findAll();

    for (Schedule schedule : schedules) {
      schedule(schedule);
    }

    log.info("Initialized {} schedules", schedules.size());
  }

  @Override
  public void cancelByActuator(Long actuatorId) {
    List<Schedule> schedules = scheduleRepository.findByActuatorId(actuatorId);

    for (Schedule s : schedules) {
      cancel(s.getId());
    }
  }

  @Override
  public void rescheduleByActuator(Long actuatorId) {
    List<Schedule> schedules = scheduleRepository.findByActuatorId(actuatorId);

    for (Schedule s : schedules) {
      schedule(s);
    }
  }

}
