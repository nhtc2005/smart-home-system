package com.group26.smart_home_system.dto.device;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeviceRequest {

  private String name;
  private Long locationId;

}
