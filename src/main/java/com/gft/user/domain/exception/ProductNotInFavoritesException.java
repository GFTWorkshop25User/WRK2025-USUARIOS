package com.gft.user.domain.exception;

public class ProductNotInFavoritesException extends RuntimeException {
    public ProductNotInFavoritesException(String message) {
        super(message);
    }
}
