package com.group26.smart_home_system.service.impl;

import static com.group26.smart_home_system.specification.SensorSpecification.hasUser;
import static com.group26.smart_home_system.specification.SensorSpecification.searchByKeyword;
import static com.group26.smart_home_system.specification.SensorSpecification.hasDevice;
import static com.group26.smart_home_system.specification.SensorSpecification.hasType;

import com.group26.smart_home_system.dto.sensor.CreateSensorRequest;
import com.group26.smart_home_system.dto.sensor.SensorFilterRequest;
import com.group26.smart_home_system.dto.sensor.SensorResponse;
import com.group26.smart_home_system.dto.sensor.UpdateSensorRequest;
import com.group26.smart_home_system.entity.Device;
import com.group26.smart_home_system.entity.Sensor;
import com.group26.smart_home_system.exception.DeviceNotFoundException;
import com.group26.smart_home_system.exception.SensorNotFoundException;
import com.group26.smart_home_system.mapper.SensorMapper;
import com.group26.smart_home_system.repository.DeviceRepository;
import com.group26.smart_home_system.repository.SensorRepository;
import com.group26.smart_home_system.security.CurrentUser;
import com.group26.smart_home_system.service.SensorService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SensorServiceImpl implements SensorService {

  private final CurrentUser currentUser;
  private final SensorRepository sensorRepository;
  private final DeviceRepository deviceRepository;
  private final SensorMapper sensorMapper;

  @Value("${device.not.found}")
  private String deviceNotFound;

  @Value("${sensor.not.found}")
  private String sensorNotFound;

  @Override
  @Transactional
  public SensorResponse createSensor(CreateSensorRequest createSensorRequest)
      throws DeviceNotFoundException {
    Sensor sensor = sensorMapper.toEntity(createSensorRequest);
    Device device = deviceRepository.findById(createSensorRequest.getDeviceId())
        .orElseThrow(() -> new DeviceNotFoundException(deviceNotFound));
    sensor.setDevice(device);
    sensorRepository.save(sensor);
    sensor.setMqttTopic("device-" + device.getId() + "-sensor-" + sensor.getId());
    return sensorMapper.toResponse(sensor);
  }

  @Override
  public List<SensorResponse> getAllSensors() {
    List<Sensor> sensors = sensorRepository.findByUserId(currentUser.getUserId());
    return sensorMapper.toResponseList(sensors);
  }

  @Override
  public SensorResponse getSensorById(Long sensorId) throws SensorNotFoundException {
    Sensor sensor = sensorRepository.findByIdAndUserId(sensorId, currentUser.getUserId())
        .orElseThrow(() -> new SensorNotFoundException(sensorNotFound));
    return sensorMapper.toResponse(sensor);
  }

  @Override
  public Page<SensorResponse> searchSensors(SensorFilterRequest sensorFilterRequest, Pageable pageable) {
    Specification<Sensor> spec = Specification
        .where(hasUser(currentUser.getUserId()))
        .and(hasDevice(sensorFilterRequest.getDeviceId()))
        .and(hasType(sensorFilterRequest.getType()))
        .and(searchByKeyword(sensorFilterRequest.getKeyword()));

    return sensorRepository.findAll(spec, pageable)
        .map(sensorMapper::toResponse);
  }

  @Override
  @Transactional
  public SensorResponse updateSensor(Long sensorId, UpdateSensorRequest updateSensorRequest)
      throws SensorNotFoundException, DeviceNotFoundException {
    Sensor sensor = sensorRepository.findByIdAndUserId(sensorId, currentUser.getUserId())
        .orElseThrow(() -> new SensorNotFoundException(sensorNotFound));
    Device device = deviceRepository.findByIdAndUserId(updateSensorRequest.getDeviceId(),
            currentUser.getUserId())
        .orElseThrow(() -> new DeviceNotFoundException(deviceNotFound));
    sensor.setName(updateSensorRequest.getName());
    sensor.setDevice(device);
    return sensorMapper.toResponse(sensorRepository.save(sensor));
  }

  @Override
  @Transactional
  public void deleteSensor(Long sensorId) throws SensorNotFoundException {
    Sensor sensor = sensorRepository.findByIdAndUserId(sensorId, currentUser.getUserId())
        .orElseThrow(() -> new SensorNotFoundException(sensorNotFound));
    sensorRepository.delete(sensor);
  }

}
