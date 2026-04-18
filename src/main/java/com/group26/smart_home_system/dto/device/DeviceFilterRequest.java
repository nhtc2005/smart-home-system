package com.group26.smart_home_system.dto.device;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeviceFilterRequest {

  private Long locationId;
  private String keyword;

}
