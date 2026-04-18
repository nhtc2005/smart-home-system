package com.group26.smart_home_system.entity;

import com.group26.smart_home_system.entity.CommandLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandLogRepository extends JpaRepository<CommandLog, Long> {

  List<CommandLog> findByActuatorId(Integer actuatorId);
}
