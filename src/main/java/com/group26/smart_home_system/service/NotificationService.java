package com.group26.smart_home_system.service;

import com.group26.smart_home_system.dto.notification.NotificationResponse;
import com.group26.smart_home_system.event.model.ActuatorStateEvent;
import java.util.List;

public interface NotificationService {

  void save(ActuatorStateEvent actuatorStateEvent);

  List<NotificationResponse> getAllNotifications();

}
