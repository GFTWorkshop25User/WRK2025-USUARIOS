package com.gft.user.domain.factory;

import com.gft.user.domain.model.user.Password;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordFactoryTest {

    Password password = null;

    @Test
    void should_ThrowIllegalArgumentException_when_password_is_null() {
        var passwordFactory = new PasswordFactory();

        var exception = assertThrows(IllegalArgumentException.class, () -> password = passwordFactory.createFromPlainText(null));

        assertEquals("Password cannot be null", exception.getMessage());
        assertNull(password);
    }

    @Test
    void should_ThrowIllegalArgumentException_when_password_is_invalid() {
        var passwordFactory = new PasswordFactory();

        String message = "Password must be at least 12 characters long and contain at least one lowercase letter" +
                ", one uppercase letter, one digit, and one special character";
        var exception = assertThrows(IllegalArgumentException.class, () -> password = passwordFactory.createFromPlainText("test"));

        assertEquals(message, exception.getMessage());
        assertNull(password);
    }

    @Test
    void should_CreatePassword_when_password_is_valid() {
        var passwordFactory = new PasswordFactory();
        Password password = passwordFactory.createFromPlainText("Password123456!");

        assertNotEquals(null, password);
    }
}
