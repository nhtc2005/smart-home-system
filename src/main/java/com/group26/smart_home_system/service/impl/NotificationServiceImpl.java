package com.group26.smart_home_system.service.impl;

import com.group26.smart_home_system.dto.notification.NotificationResponse;
import com.group26.smart_home_system.entity.Actuator;
import com.group26.smart_home_system.entity.Notification;
import com.group26.smart_home_system.event.model.ActuatorStateEvent;
import com.group26.smart_home_system.mapper.NotificationMapper;
import com.group26.smart_home_system.repository.ActuatorRepository;
import com.group26.smart_home_system.repository.NotificationRepository;
import com.group26.smart_home_system.security.CurrentUser;
import com.group26.smart_home_system.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final CurrentUser currentUser;
  private final NotificationRepository notificationRepository;
  private final ActuatorRepository actuatorRepository;
  private final NotificationMapper notificationMapper;

  @Override
  public void save(ActuatorStateEvent actuatorStateEvent) {
    Actuator actuator = actuatorRepository
        .findByIdWithDeviceAndUser(actuatorStateEvent.getActuatorId())
        .orElse(null);

    if (actuator == null) {
      return;
    }

    Notification notification = new Notification();
    notification.setMessage("Actuator " + actuatorStateEvent.getActuatorId() + " "
        + actuatorStateEvent.getActuatorState().name());
    notification.setDevice(actuator.getDevice());
    notification.setUser(actuator.getDevice().getUser());
    notificationRepository.save(notification);
  }

  @Override
  public List<NotificationResponse> getAllNotifications() {
    return notificationMapper.toResponseList(
        notificationRepository.findByUserId(currentUser.getUserId()));
  }

}
