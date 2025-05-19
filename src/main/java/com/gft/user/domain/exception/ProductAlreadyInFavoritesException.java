package com.gft.user.domain.exception;

public class ProductAlreadyInFavoritesException extends RuntimeException{

    public ProductAlreadyInFavoritesException(String message) {
        super(message);
    }
}
