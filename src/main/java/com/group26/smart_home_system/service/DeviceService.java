package com.group26.smart_home_system.service;

import com.group26.smart_home_system.dto.device.CreateDeviceRequest;
import com.group26.smart_home_system.dto.device.DeviceDetailedResponse;
import com.group26.smart_home_system.dto.device.DeviceFilterRequest;
import com.group26.smart_home_system.dto.device.DeviceResponse;
import com.group26.smart_home_system.dto.device.UpdateDeviceRequest;
import com.group26.smart_home_system.exception.DeviceNotFoundException;
import com.group26.smart_home_system.exception.LocationNotFoundException;
import com.group26.smart_home_system.exception.UserNotFoundException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface DeviceService {

  DeviceResponse createDevice(CreateDeviceRequest createDeviceRequest)
      throws UserNotFoundException, LocationNotFoundException;

  List<DeviceResponse> getAllDevices();

  DeviceDetailedResponse getDeviceById(Long deviceId) throws DeviceNotFoundException;

  Page<DeviceResponse> searchDevices(DeviceFilterRequest filter, Pageable pageable);

  DeviceResponse updateDevice(Long deviceId, UpdateDeviceRequest updateDeviceRequest)
      throws DeviceNotFoundException, LocationNotFoundException;

  void deleteDevice(Long deviceId) throws DeviceNotFoundException;

}
