package com.group26.smart_home_system.mapper;

import com.group26.smart_home_system.dto.sensor.CreateSensorRequest;
import com.group26.smart_home_system.dto.sensor.SensorResponse;
import com.group26.smart_home_system.entity.Sensor;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SensorMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "data", ignore = true)
  @Mapping(target = "device", ignore = true)
  @Mapping(target = "mqttTopic", ignore = true)
  Sensor toEntity(CreateSensorRequest createSensorRequest);

  @Mapping(source = "device.id", target = "deviceId")
  SensorResponse toResponse(Sensor sensor);

  List<SensorResponse> toResponseList(List<Sensor> sensorList);

}
