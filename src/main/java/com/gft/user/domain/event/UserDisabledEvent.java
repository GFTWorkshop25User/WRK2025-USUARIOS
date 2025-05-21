package com.gft.user.domain.event;

import java.util.UUID;

public record UserDisabledEvent(
        UUID id
) {
}
