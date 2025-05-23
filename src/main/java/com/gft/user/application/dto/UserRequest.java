package com.gft.user.application.dto;

public record UserRequest(
        String name,
        String email,
        String plainPassword
) {
}
