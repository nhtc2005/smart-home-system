package com.group26.smart_home_system.mapper;

import com.group26.smart_home_system.dto.actuator.ActuatorResponse;
import com.group26.smart_home_system.dto.actuator.CreateActuatorRequest;
import com.group26.smart_home_system.entity.Actuator;
import com.group26.smart_home_system.enums.ActuatorMode;
import com.group26.smart_home_system.enums.ActuatorState;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActuatorMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "state", expression = "java(defaultStatus())")
  @Mapping(target = "mode", expression = "java(defaultMode())")
  @Mapping(target = "device", ignore = true)
  @Mapping(target = "mqttTopic", ignore = true)
  Actuator toEntity(CreateActuatorRequest createActuatorRequest);

  @Mapping(target = "deviceId", source = "device.id")
  ActuatorResponse toResponse(Actuator actuator);

  List<ActuatorResponse> toResponseList(List<Actuator> actuatorList);

  default ActuatorState defaultStatus() {
    return ActuatorState.OFF;
  }

  default ActuatorMode defaultMode() {
    return ActuatorMode.MANUAL;
  }

}
