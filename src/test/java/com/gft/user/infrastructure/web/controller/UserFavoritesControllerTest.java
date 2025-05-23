package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.usecase.favorites.AddUserFavoriteProductUseCase;
import com.gft.user.application.usecase.favorites.GetUserFavoriteProductsUseCase;
import com.gft.user.application.usecase.favorites.GetUserIdsByFavoriteProductIdUseCase;
import com.gft.user.application.usecase.favorites.RemoveUserFavoriteProductUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
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

    @Mock
    private GetUserIdsByFavoriteProductIdUseCase getUserIdsByFavoriteProductIdUseCase;

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

        Set<Long> response = userFavoritesController.getFavorites(uuid);

        assertEquals(favoriteIds, response);

        verify(getUserFavoriteProductsUseCase, times(1)).execute(uuid);
    }

    @Test
    void should_removeFavoriteProduct_when_removeFavoriteProductCalled() {
        UUID uuid = UUID.randomUUID();
        Long productId = 1L;

        userFavoritesController.removeFavoriteProduct(uuid, productId);
        verify(removeUserFavoriteProductUseCase, times(1)).execute(uuid, productId);
    }

    @Test
    void should_obtainUserIds_when_getUserIdsWithProductIdCalled() {
        Long productId = 4L;
        List<UUID> userIds = List.of(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        );

        when(getUserIdsByFavoriteProductIdUseCase.execute(productId)).thenReturn(userIds);
        List<UUID> response = userFavoritesController.getUserIdsWithProductId(productId);

        assertEquals(userIds, response);
        verify(getUserIdsByFavoriteProductIdUseCase, times(1)).execute(productId);
    }

}
