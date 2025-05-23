package com.gft.user.application.usecase.notification;

import com.gft.user.application.service.NotificationService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateNotificationImportanceUseCase {

    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(UpdateNotificationImportanceUseCase.class);

    public UpdateNotificationImportanceUseCase(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Transactional
    public void execute(UUID notificationId, boolean importance) {
        notificationService.updateNotificationImportance(notificationId, importance);
        logger.info("Update notification [{}] importance: [{}]", notificationId, importance);
    }

}
