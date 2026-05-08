package com.group26.smart_home_system.controller;

import com.group26.smart_home_system.dto.device.CreateDeviceRequest;
import com.group26.smart_home_system.dto.device.DeviceDetailedResponse;
import com.group26.smart_home_system.dto.device.DeviceFilterRequest;
import com.group26.smart_home_system.dto.device.DeviceResponse;
import com.group26.smart_home_system.dto.device.UpdateDeviceRequest;
import com.group26.smart_home_system.exception.DeviceNotFoundException;
import com.group26.smart_home_system.exception.LocationNotFoundException;
import com.group26.smart_home_system.exception.UserNotFoundException;
import com.group26.smart_home_system.service.DeviceService;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
@Validated
public class DeviceController {

  private final DeviceService deviceService;

  @PreAuthorize("hasAnyRole('USER')")
  @PostMapping
  public ResponseEntity<DeviceResponse> createDevice(
      @Valid @RequestBody CreateDeviceRequest createDeviceRequest)
      throws UserNotFoundException, LocationNotFoundException {
    DeviceResponse deviceResponse = deviceService.createDevice(createDeviceRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(deviceResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/all")
  public ResponseEntity<List<DeviceDetailedResponse>> getAllDevices() {
    return ResponseEntity.ok(deviceService.getAllDevices());
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping("/{deviceId}")
  public ResponseEntity<DeviceDetailedResponse> getDevice(
      @PathVariable("deviceId") @Positive(message = "{device.id.invalid}") Long deviceId)
      throws DeviceNotFoundException {
    return ResponseEntity.ok(deviceService.getDeviceById(deviceId));
  }

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping
  public ResponseEntity<Page<DeviceResponse>> getDevices(
      @Valid DeviceFilterRequest deviceFilterRequest, Pageable pageable) {
    Page<DeviceResponse> deviceResponsePage =
        deviceService.searchDevices(deviceFilterRequest, pageable);
    return ResponseEntity.ok(deviceResponsePage);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @PutMapping("/{deviceId}")
  public ResponseEntity<DeviceResponse> updateDevice(
      @PathVariable("deviceId") @Positive(message = "{device.id.invalid}") Long id,
      @Valid @RequestBody UpdateDeviceRequest updateDeviceRequest)
      throws DeviceNotFoundException, LocationNotFoundException {
    DeviceResponse deviceResponse = deviceService.updateDevice(id, updateDeviceRequest);
    return ResponseEntity.ok(deviceResponse);
  }

  @PreAuthorize("hasAnyRole('USER')")
  @DeleteMapping("/{deviceId}")
  public ResponseEntity<DeviceResponse> deleteDevice(
      @PathVariable("deviceId") @Positive(message = "{device.id.invalid}") Long deviceId)
      throws DeviceNotFoundException {
    deviceService.deleteDevice(deviceId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
