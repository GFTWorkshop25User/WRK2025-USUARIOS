package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.user.favorites.AddUserFavoriteProductUseCase;
import com.gft.user.application.user.favorites.GetUserFavoriteProductsUseCase;
import com.gft.user.application.user.favorites.RemoveUserFavoriteProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserFavoritesController {

    private final GetUserFavoriteProductsUseCase getUserFavoriteProductsUseCase;
    private final AddUserFavoriteProductUseCase addUserFavoriteProductUseCase;
    private final RemoveUserFavoriteProductUseCase removeUserFavoriteProductUseCase;

    @GetMapping("/{id}/favorite-products")
    public ResponseEntity<?> getFavorites(@PathVariable UUID id) {
        return ResponseEntity.ok(getUserFavoriteProductsUseCase.execute(id));
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
}
