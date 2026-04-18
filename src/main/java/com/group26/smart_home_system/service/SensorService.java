package com.group26.smart_home_system.service;

import com.group26.smart_home_system.dto.sensor.CreateSensorRequest;
import com.group26.smart_home_system.dto.sensor.SensorFilterRequest;
import com.group26.smart_home_system.dto.sensor.SensorResponse;
import com.group26.smart_home_system.dto.sensor.UpdateSensorRequest;
import com.group26.smart_home_system.exception.DeviceNotFoundException;
import com.group26.smart_home_system.exception.SensorNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SensorService {

  SensorResponse createSensor(CreateSensorRequest createSensorRequest)
      throws DeviceNotFoundException;

  List<SensorResponse> getAllSensors();

  SensorResponse getSensorById(Long sensorId) throws SensorNotFoundException;

  Page<SensorResponse> searchSensors(SensorFilterRequest sensorFilterRequest, Pageable pageable);

  SensorResponse updateSensor(Long sensorId, UpdateSensorRequest updateSensorRequest)
      throws SensorNotFoundException, DeviceNotFoundException;

  void deleteSensor(Long sensorId) throws SensorNotFoundException;

}
