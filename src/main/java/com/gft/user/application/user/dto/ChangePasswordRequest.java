package com.gft.user.application.user.dto;

public record ChangePasswordRequest(String oldPassword, String newPassword) {
}
