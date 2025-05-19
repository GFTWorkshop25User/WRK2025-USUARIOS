package com.gft.user.application.user.management.dto;

public record ChangePasswordRequest(String oldPassword, String newPassword) {
}
