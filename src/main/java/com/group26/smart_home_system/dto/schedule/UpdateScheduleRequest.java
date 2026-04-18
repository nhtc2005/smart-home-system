package com.group26.smart_home_system.dto.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.Data;

@Data
public class UpdateScheduleResponse {

  private Integer id;
  private String action;
  private LocalTime time;
  private String mode;
  private List<Integer> days; // 0-6

}
