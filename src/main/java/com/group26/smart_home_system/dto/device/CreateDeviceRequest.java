package com.group26.smart_home_system.dto.device;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeviceRequest {

  @Positive(message = "{device.location-id.invalid}")
  private Long locationId;

  @NotBlank(message = "{device.code.required}")
  @Size(min = 2, max = 50, message = "{device.code.size}")
  private String deviceCode;

  @NotBlank(message = "{device.name.required}")
  @Size(min = 2, max = 100, message = "{device.name.size}")
  private String name;
}
