package com.group26.smart_home_system.repository;

import com.group26.smart_home_system.entity.Sensor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SensorRepository extends JpaRepository<Sensor, Long>,
    JpaSpecificationExecutor<Sensor> {

  @Query("""
      SELECT s
      FROM Sensor s
      JOIN s.device d
      JOIN d.location l
      JOIN l.user u
      WHERE u.id = :userId
      """)
  List<Sensor> findByUserId(@Param("userId") Long userId);

  @Query("""
      SELECT s
      FROM Sensor s
      JOIN s.device d
      JOIN d.location l
      WHERE s.id = :sensorId
      AND l.user.id = :userId
      """)
  Optional<Sensor> findByIdAndUserId(@Param("sensorId") Long sensorId,
      @Param("userId") Long userId);

}
