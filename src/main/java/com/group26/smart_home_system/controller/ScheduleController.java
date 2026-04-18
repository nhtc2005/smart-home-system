package com.group26.smart_home_system.controller;

import com.group26.smart_home_system.dto.schedule.CreateScheduleRequest;
import com.group26.smart_home_system.dto.schedule.ScheduleResponse;
import com.group26.smart_home_system.exception.ActuatorNotFoundException;
import com.group26.smart_home_system.exception.ScheduleNotFoundException;
import com.group26.smart_home_system.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

  private final ScheduleService scheduleService;

  @PreAuthorize("hasAnyRole('USER')")
  @PostMapping
  public ResponseEntity<ScheduleResponse> createSchedule(
      CreateScheduleRequest createScheduleRequest)
      throws ActuatorNotFoundException {
    ScheduleResponse scheduleResponse = scheduleService.createSchedule(createScheduleRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(scheduleResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @DeleteMapping("/{scheduleId}")
  public ResponseEntity<ScheduleResponse> deleteSchedule(@PathVariable("scheduleId") Long scheduleId)
      throws ScheduleNotFoundException {
    scheduleService.deleteSchedule(scheduleId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
