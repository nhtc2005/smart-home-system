package com.group26.smart_home_system.mapper;

import com.group26.smart_home_system.dto.notification.NotificationResponse;
import com.group26.smart_home_system.entity.Notification;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "device.id", target = "deviceId")
  NotificationResponse toResponse(Notification notification);

  List<NotificationResponse> toResponseList(List<Notification> notificationList);

}
