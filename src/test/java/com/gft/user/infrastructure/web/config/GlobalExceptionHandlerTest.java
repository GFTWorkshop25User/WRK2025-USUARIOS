package com.gft.user.infrastructure.web.config;

import com.gft.user.domain.exception.ProductNotInFavoritesException;
import com.gft.user.domain.exception.ProductAlreadyInFavoritesException;
import com.gft.user.infrastructure.exception.EmailAlreadyRegisteredException;
import com.gft.user.infrastructure.exception.NotificationNotFoundException;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void should_throwException_when_GenericException() {
        Exception ex = new Exception();
        ResponseEntity<Object> response = handler.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Something went wrong", response.getBody());
    }

    @Test
    void should_throwException_when_IllegalArgumentException() {

        IllegalArgumentException ex = new IllegalArgumentException("Argument is invalid");
        ResponseEntity<Object> response = handler.handleException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Argument is invalid", response.getBody());
    }

    @Test
    void should_throwException_when_UserNotFoundException() {

        UserNotFoundException ex = new UserNotFoundException("User not found");
        ResponseEntity<Object> response = handler.handleException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void should_throwException_when_ProductAlreadyInFavoritesException() {
        ProductAlreadyInFavoritesException ex = new ProductAlreadyInFavoritesException("Product is already in Favorites");
        ResponseEntity<Object> response = handler.handleException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Product is already in Favorites", response.getBody());
    }

    @Test
    void should_throwException_when_ProductNotInFavoritesException() {

        ProductNotInFavoritesException ex = new ProductNotInFavoritesException("Favorite not found");
        ResponseEntity<Object> response = handler.handleException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Favorite not found", response.getBody());
    }

    @Test
    void should_throwException_when_emailAlreadyRegistered() {
        EmailAlreadyRegisteredException ex = new EmailAlreadyRegisteredException("Email is already in registered");
        ResponseEntity<Object> response = handler.handleException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email is already in registered", response.getBody());
    }

    @Test
    void should_throwException_when_notificationNotFound() {
        NotificationNotFoundException ex = new NotificationNotFoundException("Notification not found");
        ResponseEntity<Object> response = handler.handleException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Notification not found", response.getBody());
    }

    @Test
    void should_throwException_when_resourceAccessException() {
        ResourceAccessException ex = new ResourceAccessException("Internal communication error");
        ResponseEntity<Object> response = handler.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal communication error", response.getBody());
    }
}
