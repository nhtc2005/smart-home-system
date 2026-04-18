package com.group26.smart_home_system.mapper;

import com.group26.smart_home_system.dto.schedule.CreateScheduleRequest;
import com.group26.smart_home_system.dto.schedule.ScheduleResponse;
import com.group26.smart_home_system.entity.Schedule;
import com.group26.smart_home_system.entity.ScheduleDay;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ScheduleMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "actuator", ignore = true)
  @Mapping(target = "cronExpression", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "lastExecutedAt", ignore = true)
  @Mapping(target = "days", ignore = true)
  Schedule toEntity(CreateScheduleRequest createScheduleRequest);

  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "actuator.id", target = "actuatorId")
  @Mapping(source = "days", target = "days")
  ScheduleResponse toResponse(Schedule schedule);

  List<ScheduleResponse> toResponseList(List<Schedule> scheduleList);

  default Integer map(ScheduleDay scheduleDay) {
    return scheduleDay != null ? scheduleDay.getDayOfWeek() : null;
  }

  @AfterMapping
  default void mapDays(CreateScheduleRequest request, @MappingTarget Schedule schedule) {

    if (request.getDays() == null || request.getDays().isEmpty()) return;

    List<ScheduleDay> days = request.getDays().stream()
        .map(day -> {
          ScheduleDay sd = new ScheduleDay();
          sd.setDayOfWeek(day);
          sd.setSchedule(schedule);
          return sd;
        })
        .toList();

    schedule.setDays(days);
  }

}
