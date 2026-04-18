package com.group26.smart_home_system.repository;

import com.group26.smart_home_system.entity.Schedule;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  List<Schedule> findByActuatorId(Long actuatorId);

  @Query("""
      SELECT s FROM Schedule s
      JOIN FETCH s.days
      JOIN FETCH s.actuator a
      JOIN FETCH a.device
      WHERE s.time = :time
      """)
  List<Schedule> findByTime(@Param("time") LocalTime time);

}
