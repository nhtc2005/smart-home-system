package com.group26.smart_home_system.repository;

import com.group26.smart_home_system.entity.SensorData;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

  Optional<SensorData> findTopBySensorIdOrderByTimestampDesc(Long sensorId);

  List<SensorData> findBySensorIdAndTimestampBetween(Long sensorId, Instant from, Instant to);

  Page<SensorData> findBySensorId(Long sensorId, Pageable pageable);

}
