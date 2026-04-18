package com.group26.smart_home_system.mapper;

import com.group26.smart_home_system.dto.auth.RegisterRequest;
import com.group26.smart_home_system.dto.auth.UserResponse;
import com.group26.smart_home_system.entity.User;
import com.group26.smart_home_system.enums.Role;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthenticationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", expression = "java(defaultRole())")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "locations", ignore = true)
    User toEntity(RegisterRequest registerRequest);

    UserResponse toResponse(User user);

    default Role defaultRole() {
        return Role.USER;
    }

}
