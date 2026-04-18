package com.group26.smart_home_system.repository;

import com.group26.smart_home_system.entity.Device;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface DeviceRepository extends JpaRepository<Device, Long>,
    JpaSpecificationExecutor<Device> {

  @EntityGraph(attributePaths = {"location"})
  List<Device> findByUserId(Long userId);

  @EntityGraph(attributePaths = {"location"})
  Optional<Device> findByIdAndUserId(Long id, Long userId);

  @EntityGraph(attributePaths = {"location", "sensors", "actuators"})
  @Query("SELECT d FROM Device d WHERE d.id = :id AND d.user.id = :userId")
  Optional<Device> findDetailedByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

}
