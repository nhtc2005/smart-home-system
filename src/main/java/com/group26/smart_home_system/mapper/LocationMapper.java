package com.group26.smart_home_system.mapper;

import com.group26.smart_home_system.dto.location.CreateLocationRequest;
import com.group26.smart_home_system.dto.location.LocationDetailedResponse;
import com.group26.smart_home_system.dto.location.LocationResponse;
import com.group26.smart_home_system.entity.Location;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = DeviceMapper.class)
public interface LocationMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "devices", ignore = true)
  Location toEntity(CreateLocationRequest createLocationRequest);

  @Mapping(target = "userId", source = "user.id")
  LocationResponse toResponse(Location location);

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "devices", source = "devices")
  LocationDetailedResponse toDetailedResponse(Location location);

  List<LocationResponse> toResponseList(List<Location> locationList);

}
