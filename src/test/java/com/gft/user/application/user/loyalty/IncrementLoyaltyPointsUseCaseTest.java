package com.gft.user.application.user.loyalty;

import com.gft.user.domain.model.user.*;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncrementLoyaltyPointsUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IncrementLoyaltyPointsUseCase incrementLoyaltyPointsUseCase;

    private final UUID userId = UUID.randomUUID();

    private final User user = User.create(
            UserId.create(userId),
            "Alfonso Gutierrez",
            new Email("alfonsito@gmail.com"),
            Password.createPasswordFromHashed("$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe!!"),
            new Address("","","",""),
            new HashSet<>(),
            new LoyaltyPoints(0),
            false
    );

    @Test
    void should_throwException_when_userIdDoesNotExist() {
        when(userRepository.existsByIdAndDisabledFalse(userId)).thenReturn(false);
        var exception = assertThrows(UserNotFoundException.class, () -> incrementLoyaltyPointsUseCase.execute(userId, 35));

        assertEquals(String.format("User with id %s not found", userId), exception.getMessage());
    }

    @Test
    void should_updateLoyaltyPoints_when_userIdExists() {
        when(userRepository.existsByIdAndDisabledFalse(userId)).thenReturn(true);
        when(userRepository.getById(userId)).thenReturn(user);

        incrementLoyaltyPointsUseCase.execute(userId, 25);

        assertEquals(25, user.getLoyaltyPoints().getValue());
    }
}
