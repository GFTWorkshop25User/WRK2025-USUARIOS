package com.gft.user.domain.model.user;

import com.gft.user.domain.exception.ProductNotInFavoritesException;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import com.gft.user.domain.exception.ProductAlreadyInFavoritesException;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final String name = "username";
    private final Email email = new Email("email@gft.com");
    private final Password password = Password.createPasswordFromPlain("Password123456!");

    @Test
    void should_throwIllegalArgumentException_when_nameIsNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> User.register(null, email, password));
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_nameIsBlank() {
        var exception = assertThrows(IllegalArgumentException.class, () -> User.register("", email, password));
        assertEquals("Name cannot be blank", exception.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_emailIsNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> User.register(name, null, password));
        assertEquals("Email cannot be null", exception.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_passwordIsNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> User.register(name, email, null));
        assertEquals("Password cannot be null", exception.getMessage());
    }

    @Test
    void should_createUser_when_registered() {
        var user = User.register(name, email, password);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    void should_throwIllegalArgumentException_when_changeUserNameIsNullOrBlank() {
        var user = User.register(name, email, password);
        var exception = assertThrows(IllegalArgumentException.class, () -> user.changeName(null));
        assertThrows(IllegalArgumentException.class, () -> user.changeName(" "));
        assertEquals("Name cannot be blank", exception.getMessage());
    }

    @Test
    void should_changeName_when_changeUserNameIsValid() {
        var user = User.register(name, email, password);
        user.changeName("New name");
        assertEquals("New name", user.getName());
    }
  
    @Test
    void should_addFavouriteProduct_when_isNotInFavorites() {
        var user = User.register(name, email, password);
        user.addFavoriteProduct(new FavoriteId(4L));

        assertTrue(user.getFavoriteProductIds().contains(new FavoriteId(4L)));
    }

    @Test
    void should_notAddFavouriteProduct_when_isInFavorites() {
        var user = User.register(name, email, password);
        user.addFavoriteProduct(new FavoriteId(4L));

        FavoriteId favoriteId = new FavoriteId(4L);
        assertThrows(ProductAlreadyInFavoritesException.class, () -> user.addFavoriteProduct(favoriteId));
    }
  
    @Test
    void should_throwIllegalArgumentException_when_removeFavoriteIsNull() {
        var user = User.register(name, email, password);
        assertThrows(IllegalArgumentException.class, () -> user.removeFavorite(null));
    }

    @Test
    void should_throwProductNotInFavoritesException_when_removeProductNotInFavorites() {
        User user = User.create(
                new UserId(),
                "Alfonso Gutierrez",
                new Email("alfonsito@gmail.com"),
                Password.createPasswordFromHashed("$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe!!"),
                new Address("","","",""),
                Set.of(new FavoriteId(4L), new FavoriteId(5L)),
                new LoyaltyPoints(0),
                false
        );
        FavoriteId favoriteId = new FavoriteId(2L);
        assertThrows(ProductNotInFavoritesException.class, () -> user.removeFavorite(favoriteId));
    }

    @Test
    void should_removeFavorite_when_removeFavoriteIsValid() {
        Set<FavoriteId> favorites = new HashSet<>();
        favorites.add(new FavoriteId(4L));
        favorites.add(new FavoriteId(5L));
        User user = User.create(
                new UserId(),
                "Alfonso Gutierrez",
                new Email("alfonsito@gmail.com"),
                Password.createPasswordFromHashed("$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe!!"),
                new Address("","","",""),
                favorites,
                new LoyaltyPoints(0),
                false
        );

        user.removeFavorite(new FavoriteId(5L));

        assertEquals(1, user.getFavoriteProductIds().size());
    }
}
