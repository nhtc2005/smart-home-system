package com.group26.smart_home_system.controller;

import com.group26.smart_home_system.dto.sensor.CreateSensorRequest;
import com.group26.smart_home_system.dto.sensor.SensorFilterRequest;
import com.group26.smart_home_system.dto.sensor.SensorResponse;
import com.group26.smart_home_system.dto.sensor.UpdateSensorRequest;
import com.group26.smart_home_system.exception.DeviceNotFoundException;
import com.group26.smart_home_system.exception.SensorNotFoundException;
import com.group26.smart_home_system.service.SensorService;
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
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {

  private final SensorService sensorService;

  @PreAuthorize("hasAnyRole('USER')")
  @PostMapping
  public ResponseEntity<SensorResponse> createSensor(
      @RequestBody CreateSensorRequest createSensorRequest)
      throws DeviceNotFoundException {
    SensorResponse sensorResponse = sensorService.createSensor(createSensorRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(sensorResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/all")
  public ResponseEntity<List<SensorResponse>> getAllSensors() {
    return ResponseEntity.ok(sensorService.getAllSensors());
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/{sensorId}")
  public ResponseEntity<SensorResponse> getSensor(@PathVariable("sensorId") Long sensorId)
      throws SensorNotFoundException {
    return ResponseEntity.ok(sensorService.getSensorById(sensorId));
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping
  public ResponseEntity<Page<SensorResponse>> getSensors(SensorFilterRequest sensorFilterRequest,
      Pageable pageable) {
    Page<SensorResponse> sensorResponsePage = sensorService.searchSensors(sensorFilterRequest,
        pageable);
    return ResponseEntity.ok(sensorResponsePage);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @PutMapping("/{sensorId}")
  public ResponseEntity<SensorResponse> updateSensor(@PathVariable("sensorId") Long sensorId,
      @RequestBody UpdateSensorRequest updateSensorRequest)
      throws DeviceNotFoundException, SensorNotFoundException {
    SensorResponse sensorResponse = sensorService.updateSensor(sensorId, updateSensorRequest);
    return ResponseEntity.ok(sensorResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @DeleteMapping("/{sensorId}")
  public ResponseEntity<?> deleteSensor(@PathVariable("sensorId") Long sensorId)
      throws SensorNotFoundException {
    sensorService.deleteSensor(sensorId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
