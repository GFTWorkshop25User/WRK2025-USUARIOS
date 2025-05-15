package com.gft.user.domain.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordStrengthValidatorTest {

    @Test
    void should_throwIllegalArgumentException_when_passwordIsNotSecure() {
        PasswordStrengthValidator validator = new PasswordStrengthValidator();

        assertFalse(validator.validate("Password123456"));
        assertFalse(validator.validate("Password!!!!!!"));
        assertFalse(validator.validate("Pass1!"));
        assertFalse(validator.validate("password12345!"));
    }

    @Test
    void should_returnTrue_when_passwordIsSecure() {
        PasswordStrengthValidator validator = new PasswordStrengthValidator();
        assertTrue(validator.validate("Password123456!"));
    }
}
