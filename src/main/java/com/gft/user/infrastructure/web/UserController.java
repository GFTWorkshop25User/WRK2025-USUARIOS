package com.gft.user.infrastructure.web;

import com.gft.user.application.user.UserRegistrationUseCase;
import com.gft.user.application.user.dto.UserRequest;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Generated
public class UserController {

    private final UserRegistrationUseCase userRegistrationUseCase;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucb) {
        UUID userUuid = userRegistrationUseCase.execute(userRequest);
        URI uri = ucb.path("/api/v1/users/{userUuid}").build(userUuid);

        return ResponseEntity.created(uri).build();
    }
}
