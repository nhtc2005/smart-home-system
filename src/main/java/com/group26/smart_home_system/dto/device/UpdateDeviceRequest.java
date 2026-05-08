package com.group26.smart_home_system.dto.device;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeviceRequest {

  @Positive(message = "{device.location-id.invalid}")
  private Long locationId;

  @NotBlank(message = "{device.name.required}")
  @Size(min = 2, max = 100, message = "{device.name.size}")
  private String name;
}
