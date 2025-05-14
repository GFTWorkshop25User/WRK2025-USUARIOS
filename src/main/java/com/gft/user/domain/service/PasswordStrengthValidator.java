package com.gft.user.domain.service;

public class PasswordStrengthValidator {
    private static final String PASSWORD_PATTERN =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{12,}$";

    public boolean validate(String plainPassword) {
        if (!plainPassword.matches(PASSWORD_PATTERN)) {
            throw new IllegalArgumentException("Password must be at least 12 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character");
        }
        return true;
    }
}
