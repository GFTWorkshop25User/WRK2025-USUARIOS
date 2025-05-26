package com.gft.user.application.service;

import com.gft.user.application.dto.NotificationDto;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    List<NotificationDto> getUserNotifications(UUID userId);

    void deleteNotification(UUID notificationId);

    void updateNotificationImportance(UUID notificationId, boolean importance);
}
