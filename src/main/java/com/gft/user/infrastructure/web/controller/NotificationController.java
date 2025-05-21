package com.gft.user.infrastructure.web.controller;


import com.gft.user.application.notification.usecase.DeleteNotificationUseCase;
import com.gft.user.application.notification.usecase.GetUserNotificationsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class NotificationController {

    private final GetUserNotificationsUseCase getUserNotificationsUseCase;
    private final DeleteNotificationUseCase deleteNotificationUseCase;

    @GetMapping("/users/{id}/notifications")
    public ResponseEntity<?> getUserNotifications(@PathVariable UUID id) {
        return ResponseEntity.ok(getUserNotificationsUseCase.execute(id));
    }

    @DeleteMapping("/notifications/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable UUID notificationId) {
        deleteNotificationUseCase.execute(notificationId);
    }
}
