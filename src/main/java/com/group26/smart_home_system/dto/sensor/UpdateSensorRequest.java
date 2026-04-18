package com.group26.smart_home_system.dto.sensor;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSensorRequest {

  private Long deviceId;
  private String name;

}
