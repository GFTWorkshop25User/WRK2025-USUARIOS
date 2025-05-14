package com.gft.user.domain.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FavoriteIdTest {

    @Test
    public void should_throwIllegalArgumentException_when_favoriteIdIsNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new FavoriteId(null));
        assertEquals("Product ID is not valid", exception.getMessage());
    }

    @Test
    public void should_throwIllegalArgumentException_when_favoriteIdIsNegative() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new FavoriteId(-3487347L));
        assertEquals("Product ID is not valid", exception.getMessage());
    }

    @Test
    public void should_createFavoriteId_when_favoriteIdIsPositive() {
        FavoriteId favoriteId = new FavoriteId(3487347L);
        assertEquals(3487347L, favoriteId.value());
    }
}
