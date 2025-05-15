package com.gft.user.infraestructure.exception;

import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserNotFoundExceptionTest {

    @Test
    void should_createExceptionWithMessage_when_constructorCalled() {
        String expectedMessage = "Usuario no encontrado.";
        UserNotFoundException exception = new UserNotFoundException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
