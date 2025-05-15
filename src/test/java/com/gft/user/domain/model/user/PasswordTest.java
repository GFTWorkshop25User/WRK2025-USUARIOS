package com.gft.user.domain.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void should_throwIllegalArgumentException_when_emailPasswordIsNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new Password((String) null));
        assertEquals("Password cannot be empty", exception.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_emailPasswordIsBlank() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new Password(""));
        assertEquals("Password cannot be empty", exception.getMessage());
    }

    @Test
    void should_createPassword_when_passwordIsValid() {
        String plainPassword = "Password12345";
        Password password = new Password(plainPassword);

        assertTrue(password.checkPassword(plainPassword));
    }

    @Test
    void should_createPassword_when_safeStringProvided(){
        SafeString safeString = new SafeString("GJNFDGFBYH");
        Password password = new Password(safeString);

        assertEquals(safeString.value(), password.getHashedValue());

    }

}
