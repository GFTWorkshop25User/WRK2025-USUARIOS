package com.gft.user.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductAlreadyInFavoritesExceptionTest {

    @Test
    void should_createExceptionWithMessage_when_constructorCalled(){
        String expectedMessage = "Product in favorites list already";
        ProductAlreadyInFavoritesException exception = new ProductAlreadyInFavoritesException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

}
