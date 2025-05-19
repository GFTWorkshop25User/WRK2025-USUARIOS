package com.gft.user.domain.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void should_throwIllegalArgumentException_when_createFromPlainNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> Password.createPasswordFromPlain(null));
        assertEquals("Password cannot be empty", exception.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_createFromPlainBlank() {
        var exception = assertThrows(IllegalArgumentException.class, () -> Password.createPasswordFromPlain(" "));
        assertEquals("Password cannot be empty", exception.getMessage());
    }

    @Test
    void should_createPasswordFromPlain_when_passwordIsValid() {
        String plainPassword = "Password12345!";
        Password password = Password.createPasswordFromPlain(plainPassword);

        assertTrue(password.checkPassword(plainPassword));
    }

    @Test
    void should_throwIllegalArgumentException_when_createFromHashedNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> Password.createPasswordFromHashed(null));
        assertEquals("Password cannot be empty", exception.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_createFromHashedBlank() {
        var exception = assertThrows(IllegalArgumentException.class, () -> Password.createPasswordFromHashed(" "));
        assertEquals("Password cannot be empty", exception.getMessage());
    }

    @Test
    void should_createPasswordFromHashed_when_passwordIsValid() {
        String hashedPassword = "$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe";
        Password password = Password.createPasswordFromHashed(hashedPassword);

        assertEquals(hashedPassword, password.getHashedValue());
    }

    @Test
    void should_returnTrue_when_checkCorrectPassword() {
        String plainPassword = "Password12345!";
        Password password = Password.createPasswordFromPlain(plainPassword);
        assertTrue(password.checkPassword(plainPassword));
    }
    
    @Test
    void should_changePassword_when_passwordIsValid() {
        String newPassword = "Password12345!";
        Password oldPassword = Password.createPasswordFromPlain(newPassword);

        assertTrue(oldPassword.checkPassword(newPassword));

    }
    
}
