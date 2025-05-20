package com.gft.user.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse(
        UUID id,
        LocalDateTime createdAt,
        UUID userId,
        String message,
        boolean important
) {
}
