package com.group26.smart_home_system.repository;

import com.group26.smart_home_system.entity.Location;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;

public interface LocationRepository extends JpaRepository<Location, Long> {

  List<Location> findByUserId(Long userId);

  @EntityGraph(attributePaths = {"devices"})
  Optional<Location> findByIdAndUserId(Long id, Long userId);

}