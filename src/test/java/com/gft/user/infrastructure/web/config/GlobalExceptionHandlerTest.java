package com.gft.user.infrastructure.web.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void should_throwException_when_GenericException() {
        Exception ex = new Exception("Generic exception has been thrown");
        ResponseEntity<Object> response = handler.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Generic exception has been thrown", response.getBody());
    }

    @Test
    void should_throwException_when_IllegalArgumentException() {

        IllegalArgumentException ex = new IllegalArgumentException("Argument is invalid");
        ResponseEntity<Object> response = handler.handleException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Argument is invalid", response.getBody());
    }
}
