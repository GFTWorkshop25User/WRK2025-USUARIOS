package com.gft.user.application.user.management.dto;

public record UserRequest(
        String name,
        String email,
        String plainPassword
) {
}
