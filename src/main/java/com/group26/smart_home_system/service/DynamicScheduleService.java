package com.group26.smart_home_system.service;

import com.group26.smart_home_system.entity.Schedule;

public interface DynamicScheduleService {

  void schedule(Schedule schedule);

  void cancel(Long scheduleId);

  void reschedule(Schedule schedule);

  void init();

  void cancelByActuator(Long actuatorId);

  void rescheduleByActuator(Long actuatorId);

}
