package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.user.favorites.AddUserFavoriteProductUseCase;
import com.gft.user.application.user.favorites.GetUserFavoriteProductsUseCase;
import com.gft.user.application.user.favorites.GetUserIdsByFavoriteProductIdUseCase;
import com.gft.user.application.user.favorites.RemoveUserFavoriteProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserFavoritesController {

    private final GetUserFavoriteProductsUseCase getUserFavoriteProductsUseCase;
    private final AddUserFavoriteProductUseCase addUserFavoriteProductUseCase;
    private final RemoveUserFavoriteProductUseCase removeUserFavoriteProductUseCase;
    private final GetUserIdsByFavoriteProductIdUseCase getUserIdsByFavoriteProductIdUseCase;

    @GetMapping("/{id}/favorite-products")
    public Set<Long> getFavorites(@PathVariable UUID id) {
        return getUserFavoriteProductsUseCase.execute(id);
    }

    @PutMapping("/{id}/favorite-products/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProductToFavorites(@PathVariable UUID id, @RequestBody Long productId) {
        addUserFavoriteProductUseCase.execute(id, productId);
    }

    @PutMapping("/{id}/favorite-products/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavoriteProduct(@PathVariable UUID id, @RequestBody Long productId) {
        removeUserFavoriteProductUseCase.execute(id, productId);
    }

    @GetMapping("/favorite-product/{productId}")
    public List<UUID> getUserIdsWithProductId(@PathVariable Long productId) {
        return getUserIdsByFavoriteProductIdUseCase.execute(productId);
    }

}