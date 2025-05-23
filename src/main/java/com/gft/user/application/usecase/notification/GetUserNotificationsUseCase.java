package com.gft.user.application.usecase.notification;

import com.gft.user.application.dto.NotificationDto;
import com.gft.user.application.service.NotificationService;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetUserNotificationsUseCase {

    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(GetUserNotificationsUseCase.class);

    public GetUserNotificationsUseCase(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public List<NotificationDto> execute(UUID userId) {

        if(!userRepository.existsByIdAndDisabledFalse(userId)) {
            logger.warn("Tried to get notifications from a disabled or non-existent user");
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        return notificationService.getUserNotifications(userId);
    }
}
