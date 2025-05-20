package com.gft.user.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailAlreadyRegisteredExceptionTest {

    @Test
    void should_createExceptionWithMessage_whenConstructorCalled() {
        String expectedMessage = "Email already registered";
        EmailAlreadyRegisteredException exception = new EmailAlreadyRegisteredException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

}
