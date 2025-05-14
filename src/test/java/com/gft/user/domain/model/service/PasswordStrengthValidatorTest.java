package com.gft.user.domain.model.service;

import com.gft.user.domain.service.PasswordStrengthValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordStrengthValidatorTest {

    @Test
    public void should_throwIllegalArgumentException_when_passwordIsNotSecure() {
        PasswordStrengthValidator validator = new PasswordStrengthValidator();
        String message = "Password must be at least 12 characters long and contain at least one lowercase letter" +
                                    ", one uppercase letter, one digit, and one special character";
        var exception = assertThrows(IllegalArgumentException.class, () -> validator.validate("Password123456"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("Password!!!!!!"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("Pass1!"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("password12345!"));

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void should_returnTrue_when_passwordIsSecure() {
        PasswordStrengthValidator validator = new PasswordStrengthValidator();
        assertTrue(validator.validate("Password123456!"));
    }
}
