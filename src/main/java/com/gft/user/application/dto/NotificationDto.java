package com.gft.user.application.dto;

import java.time.LocalDateTime;

public record NotificationDto(
        String message,
        LocalDateTime createdAt,
        boolean important
) {
}
