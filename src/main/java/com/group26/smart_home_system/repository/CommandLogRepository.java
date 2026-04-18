package com.group26.smart_home_system.repository;

import com.group26.smart_home_system.entity.CommandLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandLogRepository extends JpaRepository<CommandLog, Long> {

  List<CommandLog> findByActuatorId(Long actuatorId);

}
