package com.group26.smart_home_system.mapper;

import com.group26.smart_home_system.dto.sensordata.SensorDataResponse;
import com.group26.smart_home_system.entity.SensorData;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SensorDataMapper {

  @Mapping(source = "sensor.id", target = "sensorId")
  SensorDataResponse toResponse(SensorData sensorData);

  List<SensorDataResponse> toResponseList(List<SensorData> sensorDataList);

}
