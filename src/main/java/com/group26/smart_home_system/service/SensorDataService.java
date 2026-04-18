package com.group26.smart_home_system.service;

import com.group26.smart_home_system.dto.sensordata.SensorDataResponse;
import com.group26.smart_home_system.event.model.SensorDataEvent;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SensorDataService {

  SensorDataResponse getLatest(Long sensorId);

  List<SensorDataResponse> getHistory(Long sensorId, Instant from, Instant to);

  Page<SensorDataResponse> getPage(Long sensorId, Pageable pageable);

  void save(SensorDataEvent sensorDataEvent);

}
