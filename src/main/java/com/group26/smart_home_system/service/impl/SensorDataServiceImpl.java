package com.group26.smart_home_system.service.impl;

import com.group26.smart_home_system.dto.sensordata.SensorDataResponse;
import com.group26.smart_home_system.entity.Device;
import com.group26.smart_home_system.entity.Sensor;
import com.group26.smart_home_system.entity.SensorData;
import com.group26.smart_home_system.event.model.SensorDataEvent;
import com.group26.smart_home_system.mapper.SensorDataMapper;
import com.group26.smart_home_system.repository.DeviceRepository;
import com.group26.smart_home_system.repository.SensorDataRepository;
import com.group26.smart_home_system.repository.SensorRepository;
import com.group26.smart_home_system.service.SensorDataService;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorDataServiceImpl implements SensorDataService {

  private final SensorDataRepository sensorDataRepository;
  private final DeviceRepository deviceRepository;
  private final SensorRepository sensorRepository;
  private final SensorDataMapper sensorDataMapper;

  @Override
  public SensorDataResponse getLatest(Long sensorId) {
    SensorData sensorData = sensorDataRepository.findTopBySensorIdOrderByTimestampDesc(sensorId)
        .orElse(null);
    return sensorDataMapper.toResponse(sensorData);
  }

  @Override
  public List<SensorDataResponse> getHistory(Long sensorId, Instant from, Instant to) {
    return sensorDataMapper.toResponseList(
        sensorDataRepository.findBySensorIdAndTimestampBetween(sensorId, from, to));
  }

  @Override
  public Page<SensorDataResponse> getPage(Long sensorId, Pageable pageable) {
    return sensorDataRepository.findBySensorId(sensorId, pageable).map(sensorDataMapper::toResponse);
  }

  @Override
  @Transactional
  public void save(SensorDataEvent sensorDataEvent) {
    Device device = deviceRepository.findById(sensorDataEvent.getDeviceId()).orElse(null);
    if (device != null) {
      device.setLastSeen(sensorDataEvent.getTimestamp());
      deviceRepository.save(device);
    }
    Sensor sensor = sensorRepository.findById(sensorDataEvent.getSensorId()).orElse(null);
    SensorData sensorData = new SensorData();
    sensorData.setSensor(sensor);
    sensorData.setTimestamp(sensorDataEvent.getTimestamp());
    sensorData.setValue(sensorDataEvent.getValue());
    sensorDataRepository.save(sensorData);
  }

}
