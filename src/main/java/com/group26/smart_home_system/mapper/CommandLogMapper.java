package com.group26.smart_home_system.mapper;

import com.group26.smart_home_system.dto.commandlog.CommandLogResponse;
import com.group26.smart_home_system.entity.CommandLog;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommandLogMapper {

  @Mapping(source = "actuator.id", target = "actuatorId")
  CommandLogResponse toResponse(CommandLog commandLog);

  List<CommandLogResponse> toResponseList(List<CommandLog> commandLogList);

}
