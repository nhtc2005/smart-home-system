package com.group26.smart_home_system.service;

import com.group26.smart_home_system.dto.schedule.CreateScheduleRequest;
import com.group26.smart_home_system.dto.schedule.ScheduleResponse;
import com.group26.smart_home_system.exception.ActuatorNotFoundException;
import com.group26.smart_home_system.exception.ScheduleNotFoundException;
import java.util.List;

public interface ScheduleService {

  ScheduleResponse createSchedule(CreateScheduleRequest createScheduleRequest)
      throws ActuatorNotFoundException;

  List<ScheduleResponse> getScheduleByActuatorId(Long actuatorId) throws ActuatorNotFoundException;

  void deleteSchedule(Long scheduleId) throws ScheduleNotFoundException;

}
