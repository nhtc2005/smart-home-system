package com.group26.smart_home_system.controller;

import com.group26.smart_home_system.dto.actuator.ActuatorFilterRequest;
import com.group26.smart_home_system.dto.actuator.ActuatorResponse;
import com.group26.smart_home_system.dto.actuator.CreateActuatorRequest;
import com.group26.smart_home_system.dto.actuator.SetActuatorModeRequest;
import com.group26.smart_home_system.dto.actuator.SetActuatorStateRequest;
import com.group26.smart_home_system.dto.actuator.UpdateActuatorRequest;
import com.group26.smart_home_system.dto.commandlog.CommandLogResponse;
import com.group26.smart_home_system.dto.schedule.ScheduleResponse;
import com.group26.smart_home_system.exception.ActuatorNotFoundException;
import com.group26.smart_home_system.exception.DeviceNotFoundException;
import com.group26.smart_home_system.service.ActuatorService;
import com.group26.smart_home_system.service.CommandLogService;
import com.group26.smart_home_system.service.ScheduleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuators")
@RequiredArgsConstructor
public class ActuatorController {

  private final ActuatorService actuatorService;
  private final ScheduleService scheduleService;
  private final CommandLogService commandLogService;

  @PreAuthorize("hasAnyRole('USER')")
  @PostMapping
  public ResponseEntity<ActuatorResponse> createActuator(
      @RequestBody CreateActuatorRequest createActuatorRequest)
      throws DeviceNotFoundException {
    ActuatorResponse actuatorResponse = actuatorService.createActuator(createActuatorRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(actuatorResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/all")
  public ResponseEntity<List<ActuatorResponse>> getAllActuators() {
    return ResponseEntity.ok(actuatorService.getAllActuators());
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/{actuatorId}")
  public ResponseEntity<ActuatorResponse> getActuatorById(
      @PathVariable("actuatorId") Long actuatorId)
      throws ActuatorNotFoundException {
    return ResponseEntity.ok(actuatorService.getActuatorById(actuatorId));
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping
  public ResponseEntity<Page<ActuatorResponse>> searchActuators(
      ActuatorFilterRequest actuatorFilterRequest,
      Pageable pageable) {
    Page<ActuatorResponse> actuatorResponsePage = actuatorService.searchActuators(
        actuatorFilterRequest, pageable);
    return ResponseEntity.ok(actuatorResponsePage);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @PutMapping("/{actuatorId}")
  public ResponseEntity<ActuatorResponse> updateActuator(
      @PathVariable("actuatorId") Long actuatorId,
      @RequestBody UpdateActuatorRequest updateActuatorRequest)
      throws ActuatorNotFoundException, DeviceNotFoundException {
    ActuatorResponse actuatorResponse = actuatorService.updateActuator(actuatorId,
        updateActuatorRequest);
    return ResponseEntity.ok(actuatorResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @PutMapping("/{actuatorId}/state")
  public ResponseEntity<ActuatorResponse> updateActuatorState(
      @PathVariable("actuatorId") Long actuatorId,
      @RequestBody SetActuatorStateRequest setActuatorStateRequest)
      throws ActuatorNotFoundException {
    ActuatorResponse actuatorResponse = actuatorService.setActuatorState(actuatorId,
        setActuatorStateRequest);
    return ResponseEntity.ok(actuatorResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @PutMapping("/{actuatorId}/mode")
  public ResponseEntity<ActuatorResponse> updateActuatorMode(
      @PathVariable("actuatorId") Long actuatorId,
      @RequestBody SetActuatorModeRequest setActuatorModeRequest) throws ActuatorNotFoundException {
    ActuatorResponse actuatorResponse = actuatorService.setActuatorMode(actuatorId,
        setActuatorModeRequest);
    return ResponseEntity.ok(actuatorResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @DeleteMapping("/{actuatorId}")
  public ResponseEntity<?> deleteActuator(@PathVariable("actuatorId") Long actuatorId)
      throws ActuatorNotFoundException {
    actuatorService.deleteActuator(actuatorId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/{actuatorId}/command-logs")
  public ResponseEntity<List<CommandLogResponse>> getActuatorCommandLogs(
      @PathVariable("actuatorId") Long actuatorId) {
    return ResponseEntity.ok(commandLogService.getCommandLogsByActuatorId(actuatorId));
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/{actuatorId}/schedules")
  public ResponseEntity<List<ScheduleResponse>> getActuatorSchedules(
      @PathVariable("actuatorId") Long actuatorId) throws ActuatorNotFoundException {
    return ResponseEntity.ok(scheduleService.getScheduleByActuatorId(actuatorId));
  }

}
