package com.gft.user.infrastructure.dto;

import java.util.UUID;

public record UserIncrementLoyaltyPointsDto(
        UUID userId,
        int loyaltyPoints
) {
}
