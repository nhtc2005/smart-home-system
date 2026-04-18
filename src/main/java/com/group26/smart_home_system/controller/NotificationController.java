package com.group26.smart_home_system.controller;

import com.group26.smart_home_system.dto.notification.NotificationResponse;
import com.group26.smart_home_system.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @PreAuthorize("hasAnyRole('USER')")
  @GetMapping
  public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
    return ResponseEntity.ok(notificationService.getAllNotifications());
  }

}
