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
class GetUserLoyaltyPointsUseCaseTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserLoyaltyPointsUseCase getUserLoyaltyPointsUseCase;

    @Test
    void should_returnPoints_when_execute() {
        UUID uuid = UUID.randomUUID();
        User user = User.create(
                new UserId(),
                "miguel",
                new Email("miguel@gmail.com"),
                Password.createPasswordFromHashed("$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe!!"),
                new Address("Spain", "241852", "Villalba", "Calle los floriponcios"),
                new HashSet<>(),
                new LoyaltyPoints(20),
                false
        );

        when(userRepository.existsById(uuid)).thenReturn(true);
        when(userRepository.getById(uuid)).thenReturn(user);

        int points = getUserLoyaltyPointsUseCase.execute(uuid);

        assertEquals(20, points);

    }

    @Test
    void should_throwException_when_executeUUIDNotFound() {
        UUID uuid = UUID.randomUUID();
        when(userRepository.existsById(uuid)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> getUserLoyaltyPointsUseCase.execute(uuid));
    }

}
