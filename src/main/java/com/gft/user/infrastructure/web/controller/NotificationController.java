package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.dto.NotificationDto;
import com.gft.user.application.usecase.notification.DeleteNotificationUseCase;
import com.gft.user.application.usecase.notification.GetUserNotificationsUseCase;
import com.gft.user.application.usecase.notification.UpdateNotificationImportanceUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class NotificationController {

    private final GetUserNotificationsUseCase getUserNotificationsUseCase;
    private final DeleteNotificationUseCase deleteNotificationUseCase;
    private final UpdateNotificationImportanceUseCase updateNotificationImportanceUseCase;

    @GetMapping("/users/{id}/notifications")
    public List<NotificationDto> getUserNotifications(@PathVariable UUID id) {
        return getUserNotificationsUseCase.execute(id);
    }

    @DeleteMapping("/notifications/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable UUID notificationId) {
        deleteNotificationUseCase.execute(notificationId);
    }

    @PatchMapping("/notifications/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNotificationImportance(@PathVariable UUID notificationId, @RequestBody boolean importance) {
        updateNotificationImportanceUseCase.execute(notificationId, importance);
    }
}
