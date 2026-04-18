package com.group26.smart_home_system.dto.device;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeviceRequest {

  private Long locationId;
  private String deviceCode;
  private String name;

}
