package com.gft.user.infrastructure.mapper;

import com.gft.user.application.dto.NotificationDto;
import com.gft.user.infrastructure.dto.NotificationResponse;
import org.springframework.stereotype.Service;

@Service
public class NotificationMapper {

    public NotificationDto toNotificationDto(NotificationResponse notificationResponse) {
        return new NotificationDto(
                notificationResponse.message(),
                notificationResponse.createdAt(),
                notificationResponse.important()
        );
    }
}
