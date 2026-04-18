package com.group26.smart_home_system.repository;

import com.group26.smart_home_system.entity.Actuator;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActuatorRepository extends JpaRepository<Actuator, Long>,
    JpaSpecificationExecutor<Actuator> {

  @Query("""
      SELECT a
      FROM Actuator a
      JOIN a.device d
      JOIN d.location l
      JOIN l.user u
      WHERE u.id = :userId
      """)
  List<Actuator> findByUserId(@Param("userId") Long userId);

  @Query("""
      SELECT a
      FROM Actuator a
      JOIN a.device d
      JOIN d.location l
      WHERE a.id = :actuatorId
      AND l.user.id = :userId
      """)
  Optional<Actuator> findByIdAndUserId(
      @Param("actuatorId") Long actuatorId,
      @Param("userId") Long userId
  );

  @Query("""
      SELECT a FROM Actuator a
      JOIN FETCH a.device d
      JOIN FETCH d.user
      WHERE a.id = :id
      """)
  Optional<Actuator> findByIdWithDeviceAndUser(@Param("id") Long actuatorId);

}
