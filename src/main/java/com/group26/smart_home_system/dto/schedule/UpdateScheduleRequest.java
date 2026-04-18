package com.group26.smart_home_system.dto.schedule;

import com.group26.smart_home_system.enums.ActuatorState;
import com.group26.smart_home_system.enums.ScheduleMode;
import java.time.LocalTime;
import java.util.List;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateScheduleRequest {

  private Long id;
  private ScheduleMode mode;
  private List<Integer> days;
  private LocalTime time;
  private ActuatorState action;

}
