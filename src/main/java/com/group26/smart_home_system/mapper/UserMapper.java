package com.group26.smart_home_system.mapper;

import com.group26.smart_home_system.dto.user.UpdateUserRequest;
import com.group26.smart_home_system.dto.user.UserResponse;
import com.group26.smart_home_system.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

  UserResponse toUserResponse(User user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateUserFromRequest(UpdateUserRequest request, @MappingTarget User user);
}
