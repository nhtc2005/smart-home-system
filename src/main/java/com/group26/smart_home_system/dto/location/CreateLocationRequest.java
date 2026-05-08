package com.group26.smart_home_system.dto.location;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateLocationRequest {

  @NotBlank(message = "{location.name.required}")
  @Size(min = 2, max = 100, message = "{location.name.size}")
  private String name;
}
