package com.gft.user.infrastructure.web;

import com.gft.user.application.user.*;
import com.gft.user.application.user.dto.ChangePasswordRequest;
import com.gft.user.application.user.dto.UserRequest;
import com.gft.user.domain.model.user.Address;
import com.gft.user.domain.model.user.FavoriteId;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserRegistrationUseCase userRegistrationUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ChangeUserNameUseCase changeUserNameUseCase;
    private final ChangeEmailUseCase changeEmailUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final ChangeAddressUseCase changeAddressUseCase;
    private final GetFavoriteProductsUseCase getFavoriteProductsUseCase;
    private final GetUserLoyaltyPointsUseCase userLoyaltyPointsUseCase;
    private final AddUserFavoriteProductUseCase addUserFavoriteProductUseCase;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucb) {
        UUID userUuid = userRegistrationUseCase.execute(userRequest);
        URI uri = ucb.path("/api/v1/users/{userUuid}").build(userUuid);

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(getUserByIdUseCase.execute(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        deleteUserUseCase.execute(id);
    }

    @PutMapping("/{id}/change-name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserName(@PathVariable UUID id, @RequestBody String newName) {
        changeUserNameUseCase.execute(id, newName);
    }

    @PutMapping("/{id}/change-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void put(@PathVariable UUID id, @RequestBody String email) { changeEmailUseCase.execute(id, email); }

    @PutMapping("/{id}/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable UUID id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        changePasswordUseCase.execute(id, changePasswordRequest.oldPassword(), changePasswordRequest.newPassword());
    }

    @PutMapping("/{id}/change-address")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAddress(@PathVariable UUID id, @RequestBody Address address) {
        changeAddressUseCase.execute(id, address);
    }

    @GetMapping("/{id}/loyalty-points")
    public ResponseEntity<?> getUserLoyaltyPoints(@PathVariable UUID id){
        return ResponseEntity.ok(userLoyaltyPointsUseCase.execute(id));
    }

    @PutMapping("/{id}/favorite-products/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProductToFavorites(@PathVariable UUID id, @RequestBody Long productId) {
        addUserFavoriteProductUseCase.execute(id, productId);
    }

    @GetMapping("/{id}/favorite-products")
    public ResponseEntity<?> getFavorites(@PathVariable UUID id) {
        return ResponseEntity.ok(getFavoriteProductsUseCase.execute(id));
    }
}
