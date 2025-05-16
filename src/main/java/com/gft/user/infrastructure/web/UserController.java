package com.gft.user.infrastructure.web;

import com.gft.user.application.user.ChangeEmailUseCase;
import com.gft.user.application.user.GetUserByIdUseCase;
import com.gft.user.application.user.DeleteUserUseCase;
import com.gft.user.application.user.UserRegistrationUseCase;
import com.gft.user.application.user.dto.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserRegistrationUseCase userRegistrationUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ChangeEmailUseCase changeEmailUseCase;

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

    @PutMapping("/{id}/changeEmail")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void put(@PathVariable UUID id, @RequestBody String email) { changeEmailUseCase.execute(id, email); }
}
