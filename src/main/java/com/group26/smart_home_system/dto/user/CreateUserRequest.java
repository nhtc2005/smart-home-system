package com.group26.smart_home_system.dto.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private String password;

}
