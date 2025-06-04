package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.usecase.loyalty.DecrementLoyaltyPointsUseCase;
import com.gft.user.application.usecase.loyalty.GetUserLoyaltyPointsUseCase;
import com.gft.user.application.usecase.loyalty.IncrementLoyaltyPointsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserLoyaltyControllerTest {

    @InjectMocks
    private UserLoyaltyController userLoyaltyController;

    @Mock
    private GetUserLoyaltyPointsUseCase getUserLoyaltyPointsUseCase;

    @Mock
    private DecrementLoyaltyPointsUseCase decrementLoyaltyPointsUseCase;

    @Mock
    private IncrementLoyaltyPointsUseCase incrementLoyaltyPointsUseCase;

    @Test
    void should_obtainLoyaltyPoints_when_obtainLoyaltyPointsCalled() {
        UUID uuid = UUID.randomUUID();
        when(getUserLoyaltyPointsUseCase.execute(uuid)).thenReturn(4);

        int response = userLoyaltyController.getUserLoyaltyPoints(uuid);

        assertEquals(4, response);

        verify(getUserLoyaltyPointsUseCase, times(1)).execute(uuid);
    }

    @Test
    void should_incrementLoyaltyPoints_when_incrementCalled() {
        UUID uuid = UUID.randomUUID();
        int points = 10;

        userLoyaltyController.incrementUserLoyaltyPoints(uuid, points);

        verify(incrementLoyaltyPointsUseCase, times(1)).execute(uuid, points);
        verifyNoMoreInteractions(incrementLoyaltyPointsUseCase);
    }

    @Test
    void should_decrementLoyaltyPoints_when_decrementCalled() {
        UUID uuid = UUID.randomUUID();
        int points = 5;

        userLoyaltyController.decrementUserLoyaltyPoints(uuid, points);

        verify(decrementLoyaltyPointsUseCase, times(1)).execute(uuid, points);
        verifyNoMoreInteractions(decrementLoyaltyPointsUseCase);
    }
}
