package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.user.favorites.AddUserFavoriteProductUseCase;
import com.gft.user.application.user.favorites.GetUserFavoriteProductsUseCase;
import com.gft.user.application.user.favorites.RemoveUserFavoriteProductUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserFavoritesControllerTest {

    @InjectMocks
    private UserFavoritesController userFavoritesController;

    @Mock
    private AddUserFavoriteProductUseCase addUserFavoriteProductUseCase;

    @Mock
    private GetUserFavoriteProductsUseCase getUserFavoriteProductsUseCase;

    @Mock
    private RemoveUserFavoriteProductUseCase removeUserFavoriteProductUseCase;

    @Test
    void should_addFavorite_when_addProductToFavoritesCalled() {
        UUID uuid = UUID.randomUUID();
        userFavoritesController.addProductToFavorites(uuid,4L);

        verify(addUserFavoriteProductUseCase, times(1)).execute(uuid, 4L);
    }

    @Test
    void should_obtainFavoriteIds_when_obtainFavoriteIdsCalled() {
        UUID uuid = UUID.randomUUID();
        Set<Long> favoriteIds = new HashSet<>();
        favoriteIds.add(4L);
        when(getUserFavoriteProductsUseCase.execute(uuid)).thenReturn(favoriteIds);

        ResponseEntity<?> response = userFavoritesController.getFavorites(uuid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(favoriteIds, response.getBody());

        verify(getUserFavoriteProductsUseCase, times(1)).execute(uuid);
    }

    @Test
    void should_removeFavoriteProduct_when_removeFavoriteProductCalled() {
        UUID uuid = UUID.randomUUID();
        Long productId = 1L;

        userFavoritesController.removeFavoriteProduct(uuid, productId);

        verify(removeUserFavoriteProductUseCase, times(1)).execute(uuid, productId);
    }
}
