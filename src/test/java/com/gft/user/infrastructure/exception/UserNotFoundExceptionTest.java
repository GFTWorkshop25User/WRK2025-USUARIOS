package com.gft.user.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserNotFoundExceptionTest {

    @Test
    void should_createExceptionWithMessage_when_constructorCalled() {
        String expectedMessage = "User not found";
        UserNotFoundException exception = new UserNotFoundException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
