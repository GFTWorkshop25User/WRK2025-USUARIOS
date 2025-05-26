package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.usecase.loyalty.GetUserLoyaltyPointsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserLoyaltyControllerTest {

    @InjectMocks
    private UserLoyaltyController userLoyaltyController;

    @Mock
    private GetUserLoyaltyPointsUseCase getUserLoyaltyPointsUseCase;

    @Test
    void should_obtainLoyaltyPoints_when_obtainLoyaltyPointsCalled() {
        UUID uuid = UUID.randomUUID();
        when(getUserLoyaltyPointsUseCase.execute(uuid)).thenReturn(4);

        int response = userLoyaltyController.getUserLoyaltyPoints(uuid);

        assertEquals(4, response);

        verify(getUserLoyaltyPointsUseCase, times(1)).execute(uuid);
    }
}
