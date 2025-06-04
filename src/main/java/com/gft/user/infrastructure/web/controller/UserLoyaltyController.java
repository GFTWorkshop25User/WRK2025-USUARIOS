package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.usecase.loyalty.DecrementLoyaltyPointsUseCase;
import com.gft.user.application.usecase.loyalty.GetUserLoyaltyPointsUseCase;
import com.gft.user.application.usecase.loyalty.IncrementLoyaltyPointsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserLoyaltyController {

    private final GetUserLoyaltyPointsUseCase userLoyaltyPointsUseCase;
    private final DecrementLoyaltyPointsUseCase decrementLoyaltyPointsUseCase;
    private final IncrementLoyaltyPointsUseCase incrementLoyaltyPointsUseCase;


    @GetMapping("/{id}/loyalty-points")
    public int getUserLoyaltyPoints(@PathVariable UUID id){
        return userLoyaltyPointsUseCase.execute(id);
    }

    @PatchMapping("/{id}/loyalty-points/increment")
    public void incrementUserLoyaltyPoints(@PathVariable UUID id, @RequestBody int points) {
        incrementLoyaltyPointsUseCase.execute(id, points);
    }

    @PatchMapping("/{id}/loyalty-points/decrement")
    public void decrementUserLoyaltyPoints(@PathVariable UUID id, @RequestBody int points) {
        decrementLoyaltyPointsUseCase.execute(id, points);
    }

}
