package com.group26.smart_home_system.dto.actuator;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateActuatorRequest {

  private Long deviceId;
  private String name;

}
