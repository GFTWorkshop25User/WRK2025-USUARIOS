package com.gft.user.application.user;

import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserLoyaltyPointsUseCaseTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserLoyaltyPointsUseCase getUserLoyaltyPointsUseCase;

    @Test
    void should_returnPoints_when_execute() {
        UUID uuid = UUID.randomUUID();

        when(userRepository.existsById(uuid)).thenReturn(true);

        when(userRepository.getLoyaltyPoints(uuid)).thenReturn(1);

        int points = getUserLoyaltyPointsUseCase.execute(uuid);

        assertEquals(1, points);

    }

    @Test
    void should_throwException_when_executeUUIDNotFound() {
        UUID uuid = UUID.randomUUID();
        when(userRepository.existsById(uuid)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> getUserLoyaltyPointsUseCase.execute(uuid));
    }

}
