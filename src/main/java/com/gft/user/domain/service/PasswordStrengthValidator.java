package com.gft.user.domain.service;

public class PasswordStrengthValidator {
    private static final String PASSWORD_PATTERN =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{12,}$";

    public boolean validate(String plainPassword) {
        return plainPassword.matches(PASSWORD_PATTERN);
    }
}
