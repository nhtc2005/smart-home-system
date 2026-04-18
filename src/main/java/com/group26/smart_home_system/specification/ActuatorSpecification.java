package com.group26.smart_home_system.specification;

import com.group26.smart_home_system.entity.Actuator;
import com.group26.smart_home_system.enums.ActuatorType;
import org.springframework.data.jpa.domain.Specification;

public class ActuatorSpecification {

  public static Specification<Actuator> hasUser(Long userId) {
    return (root, query, cb) ->
        userId == null
            ? cb.conjunction()
            : cb.equal(root.get("device").get("user").get("id"), userId);
  }

  public static Specification<Actuator> hasDevice(Long deviceId) {
    return (root, query, cb) ->
        deviceId == null
            ? cb.conjunction()
            : cb.equal(root.get("device").get("id"), deviceId);
  }

  public static Specification<Actuator> hasType(ActuatorType type) {
    return (root, query, cb) ->
        type == null
            ? cb.conjunction()
            : cb.equal(root.get("type"), type);
  }

  public static Specification<Actuator> searchByKeyword(String keyword) {
    return (root, query, cb) -> {
      if (keyword == null || keyword.isBlank()) {
        return cb.conjunction();
      }

      String likePattern = "%" + keyword.toLowerCase() + "%";

      return cb.or(
          cb.like(cb.lower(root.get("name")), likePattern),
          cb.like(cb.lower(root.get("type").as(String.class)), likePattern),
          cb.like(cb.lower(root.get("status").as(String.class)), likePattern),
          cb.like(cb.lower(root.get("mode").as(String.class)), likePattern)
      );
    };
  }

}
