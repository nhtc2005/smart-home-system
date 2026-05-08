package com.group26.smart_home_system.dto.device;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeviceFilterRequest {

  @Positive(message = "{device.location-id.invalid}")
  private Long locationId;

  @Size(max = 100, message = "{device.keyword.size}")
  private String keyword;
}
