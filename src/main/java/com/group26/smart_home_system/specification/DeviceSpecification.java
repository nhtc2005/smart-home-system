package com.group26.smart_home_system.specification;

import com.group26.smart_home_system.entity.Device;
import org.springframework.data.jpa.domain.Specification;

public class DeviceSpecification {

  public static Specification<Device> hasUser(Long userId) {
    return (root, query, cb) ->
        userId == null
            ? cb.conjunction()
            : cb.equal(root.get("user").get("id"), userId);
  }

  public static Specification<Device> hasLocation(Long locationId) {
    return (root, query, cb) ->
        locationId == null
            ? cb.conjunction()
            : cb.equal(root.get("location").get("id"), locationId);
  }

  public static Specification<Device> searchByKeyword(String keyword) {
    return (root, query, cb) -> {
      if (keyword == null || keyword.isBlank()) {
        return cb.conjunction();
      }

      String like = "%" + keyword.toLowerCase() + "%";

      return cb.or(
          cb.like(cb.lower(root.get("name")), like),
          cb.like(cb.lower(root.get("deviceCode")), like)
      );
    };
  }

}
