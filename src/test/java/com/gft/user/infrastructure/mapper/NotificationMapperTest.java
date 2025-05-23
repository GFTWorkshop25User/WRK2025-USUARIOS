package com.gft.user.infrastructure.mapper;

import com.gft.user.application.dto.NotificationDto;
import com.gft.user.infrastructure.dto.NotificationResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationMapperTest {

    NotificationMapper notificationMapper = new NotificationMapper();

    @Test
    void should_mapNotification() {
        LocalDateTime now = LocalDateTime.now();

        NotificationResponse notificationResponse = new NotificationResponse(
                UUID.randomUUID(),
                now,
                UUID.randomUUID(),
                "Message",
                true
        );

        NotificationDto notificationDto = new NotificationDto(
                "Message",
                now,
                true
        );

        assertEquals(notificationDto, notificationMapper.toNotificationDto(notificationResponse));
    }
}
