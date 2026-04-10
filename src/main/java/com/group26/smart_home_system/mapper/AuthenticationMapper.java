package com.group26.smart_home_system.mapper;

import com.group26.smart_home_system.dto.auth.RegisterRequest;
import com.group26.smart_home_system.dto.auth.UserResponse;
import com.group26.smart_home_system.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthenticationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User registerRequestToUser(RegisterRequest registerRequest);

    @Mapping(target = "role", expression = "java(user.getRole().name())")
    UserResponse userToUserResponse(User user);

}
