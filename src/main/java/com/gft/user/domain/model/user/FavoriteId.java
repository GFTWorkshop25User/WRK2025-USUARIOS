package com.gft.user.domain.model.user;

public record FavoriteId(Long value) {

    public FavoriteId {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Product ID is not valid");
        }
    }
}
