package com.group26.smart_home_system.controller;

import com.group26.smart_home_system.dto.schedule.CreateScheduleRequest;
import com.group26.smart_home_system.dto.schedule.ScheduleResponse;
import com.group26.smart_home_system.enums.ScheduleMode;
import com.group26.smart_home_system.exception.ActuatorNotFoundException;
import com.group26.smart_home_system.exception.ScheduleNotFoundException;
import com.group26.smart_home_system.service.ScheduleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@Validated
public class ScheduleController {

  private final ScheduleService scheduleService;

  @PreAuthorize("hasAnyRole('USER')")
  @PostMapping
  public ResponseEntity<ScheduleResponse> createSchedule(
      @Valid @RequestBody CreateScheduleRequest createScheduleRequest)
      throws ActuatorNotFoundException {
    validateSchedule(createScheduleRequest);
    ScheduleResponse scheduleResponse = scheduleService.createSchedule(createScheduleRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(scheduleResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @DeleteMapping("/{scheduleId}")
  public ResponseEntity<ScheduleResponse> deleteSchedule(
      @PathVariable("scheduleId") @Positive(message = "{schedule.id.invalid}") Long scheduleId)
      throws ScheduleNotFoundException {
    scheduleService.deleteSchedule(scheduleId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private void validateSchedule(CreateScheduleRequest request) {

    if (request.getMode() == ScheduleMode.WEEKLY && CollectionUtils.isEmpty(request.getDays())) {
      throw new IllegalArgumentException("Days are required for WEEKLY schedule");
    }

    if (request.getMode() != ScheduleMode.WEEKLY && !CollectionUtils.isEmpty(request.getDays())) {
      throw new IllegalArgumentException("Days are only allowed for WEEKLY schedule");
    }
  }
}
