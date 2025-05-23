package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.user.management.dto.ChangePasswordRequest;
import com.gft.user.application.user.management.dto.UserRequest;
import com.gft.user.application.user.management.*;
import com.gft.user.domain.model.user.Address;
import com.gft.user.domain.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserManagementController {

    private final UserRegistrationUseCase userRegistrationUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ChangeUserNameUseCase changeUserNameUseCase;
    private final ChangeEmailUseCase changeEmailUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final ChangeAddressUseCase changeAddressUseCase;

    @PostMapping
    public ResponseEntity<UUID> registerUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegistrationUseCase.execute(userRequest));
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable UUID id) {
        return getUserByIdUseCase.execute(id);
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
    public void changeEmail(@PathVariable UUID id, @RequestBody String email) {
        changeEmailUseCase.execute(id, email);
    }

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
}