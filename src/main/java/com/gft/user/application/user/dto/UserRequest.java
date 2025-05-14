package com.gft.user.application.user.dto;

public record UserRequest(
        String name,
        String email,
        String plainPassword
) {
}
