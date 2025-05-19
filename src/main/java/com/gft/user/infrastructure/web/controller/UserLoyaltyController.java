package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.user.loyalty.GetUserLoyaltyPointsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserLoyaltyController {

    private final GetUserLoyaltyPointsUseCase userLoyaltyPointsUseCase;


    @GetMapping("/{id}/loyalty-points")
    public ResponseEntity<?> getUserLoyaltyPoints(@PathVariable UUID id){
        return ResponseEntity.ok(userLoyaltyPointsUseCase.execute(id));
    }
}
