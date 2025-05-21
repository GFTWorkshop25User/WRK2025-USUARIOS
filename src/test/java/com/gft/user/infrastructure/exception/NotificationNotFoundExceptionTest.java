package com.gft.user.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationNotFoundExceptionTest {

    @Test
    void should_createExceptionWithMessage_when_constructorCalled() {
        String expectedMessage = "Notification not found";
        UserNotFoundException exception = new UserNotFoundException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
