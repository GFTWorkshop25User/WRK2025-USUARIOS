package com.gft.user.application.dto;

public record ChangePasswordRequest(String oldPassword, String newPassword) {
}
