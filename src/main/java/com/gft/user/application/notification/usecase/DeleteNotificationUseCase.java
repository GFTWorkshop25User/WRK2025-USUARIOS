package com.gft.user.application.notification.usecase;

import com.gft.user.application.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteNotificationUseCase {

    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(DeleteNotificationUseCase.class);

    public DeleteNotificationUseCase(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void execute(UUID notificationId) {
        logger.info("Deleting notification with id {}", notificationId);
        notificationService.deleteNotification(notificationId);
    }
}
