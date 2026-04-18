package com.group26.smart_home_system.dto.common;

import java.time.Instant;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

  private String message;
  private HttpStatus error;
  private Integer status;
  private Instant timestamp;

}
