package com.group26.smart_home_system.controller;

import com.group26.smart_home_system.dto.sensordata.SensorDataResponse;
import com.group26.smart_home_system.service.SensorDataService;
import java.time.Instant;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensor-data")
@RequiredArgsConstructor
@Validated
public class SensorDataController {

  private final SensorDataService service;

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/latest")
  public ResponseEntity<SensorDataResponse> getLatest(
      @RequestParam @Positive(message = "{sensor.id.invalid}") Long sensorId) {
    return ResponseEntity.ok(service.getLatest(sensorId));
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/history")
  public ResponseEntity<?> getHistory(
      @RequestParam @Positive(message = "{sensor.id.invalid}") Long sensorId,
      @RequestParam Instant from,
      @RequestParam Instant to) {
    return ResponseEntity.ok(service.getHistory(sensorId, from, to));
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping
  public ResponseEntity<?> getPage(
      @RequestParam @Positive(message = "{sensor.id.invalid}") Long sensorId, Pageable pageable) {
    return ResponseEntity.ok(service.getPage(sensorId, pageable));
  }
}
