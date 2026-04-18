package com.group26.smart_home_system.dto.schedule;

import com.group26.smart_home_system.enums.ActuatorState;
import com.group26.smart_home_system.enums.ScheduleMode;
import java.time.Instant;
import lombok.*;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleResponse {

  private Long id;
  private Long userId;
  private Long actuatorId;
  private ScheduleMode mode;
  private List<Integer> days;
  private LocalTime time;
  private ActuatorState action;
  private Instant createdAt;
  private Instant lastExecutedAt;

}
