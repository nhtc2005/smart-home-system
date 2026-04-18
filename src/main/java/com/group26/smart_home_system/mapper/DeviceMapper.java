package com.group26.smart_home_system.mapper;

import com.group26.smart_home_system.dto.device.CreateDeviceRequest;
import com.group26.smart_home_system.dto.device.DeviceDetailedResponse;
import com.group26.smart_home_system.dto.device.DeviceResponse;
import com.group26.smart_home_system.entity.Device;
import java.util.List;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {ActuatorMapper.class, SensorMapper.class}
)
public interface DeviceMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastSeen", ignore = true)
  @Mapping(target = "location", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "sensors", ignore = true)
  @Mapping(target = "actuators", ignore = true)
  Device toEntity(CreateDeviceRequest createDeviceRequest);

  @Mapping(target = "locationName", source = "location.name")
  DeviceResponse toResponse(Device device);

  @Mapping(target = "locationName", source = "location.name")
  @Mapping(target = "sensors", source = "sensors")
  @Mapping(target = "actuators", source = "actuators")
  DeviceDetailedResponse toDetailedResponse(Device device);

  List<DeviceResponse> toResponseList(List<Device> deviceList);

}
