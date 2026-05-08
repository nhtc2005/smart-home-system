package com.group26.smart_home_system.dto.schedule;

import com.group26.smart_home_system.enums.ActuatorState;
import com.group26.smart_home_system.enums.ScheduleMode;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateScheduleRequest {

  @Positive(message = "{schedule.actuator-id.invalid}")
  private Long actuatorId;

  @NotNull(message = "{schedule.mode.required}")
  private ScheduleMode mode;

  @Size(max = 7, message = "{schedule.days.size}")
  private List<
          @Min(value = 1, message = "{schedule.day.min}")
          @Max(value = 7, message = "{schedule.day.max}") Integer>
      days;

  @NotNull(message = "{schedule.time.required}")
  private LocalTime time;

  @NotNull(message = "{schedule.action.required}")
  private ActuatorState action;
}
