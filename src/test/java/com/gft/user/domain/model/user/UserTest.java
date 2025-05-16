package com.gft.user.domain.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    private final String name = "username";
    private final Email email = new Email("email@gft.com");
    private final Password password = Password.createPasswordFromPlain("Password123456!");

    @Test
    void should_throwIllegalArgumentException_when_nameIsNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> User.register(null, email, password));
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_nameIsBlank() {
        var exception = assertThrows(IllegalArgumentException.class, () -> User.register("", email, password));
        assertEquals("Name cannot be blank", exception.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_emailIsNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> User.register(name, null, password));
        assertEquals("Email cannot be null", exception.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_passwordIsNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> User.register(name, email, null));
        assertEquals("Password cannot be null", exception.getMessage());
    }

    @Test
    void should_createUser_when_registered() {
        var user = User.register(name, email, password);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }
}
