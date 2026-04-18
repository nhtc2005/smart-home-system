package com.group26.smart_home_system.specification;

import com.group26.smart_home_system.entity.Sensor;
import com.group26.smart_home_system.enums.SensorType;
import org.springframework.data.jpa.domain.Specification;

public class SensorSpecification {

  public static Specification<Sensor> hasUser(Long userId) {
    return (root, query, cb) ->
        userId == null ? cb.conjunction()
            : cb.equal(root.get("device").get("location").get("user").get("id"), userId);
  }

  public static Specification<Sensor> hasDevice(Long deviceId) {
    return (root, query, cb) ->
        deviceId == null ? cb.conjunction()
            : cb.equal(root.get("device").get("id"), deviceId);
  }

  public static Specification<Sensor> hasType(SensorType type) {
    return (root, query, cb) ->
        type == null ? cb.conjunction()
            : cb.equal(root.get("type"), type);
  }

  public static Specification<Sensor> searchByKeyword(String keyword) {
    return (root, query, cb) -> {
      if (keyword == null || keyword.isBlank()) {
        return cb.conjunction();
      }

      String like = "%" + keyword.toLowerCase() + "%";

      return cb.or(
          cb.like(cb.lower(root.get("name")), like),
          cb.like(cb.lower(root.get("mqttTopic")), like)
      );
    };
  }

}
