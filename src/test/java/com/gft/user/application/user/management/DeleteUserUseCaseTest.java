package com.gft.user.application.user.management;

import com.gft.user.domain.event.DomainEventPublisher;
import com.gft.user.domain.event.UserDisabledEvent;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DomainEventPublisher domainEventPublisher;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

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
        assertThrows(UserNotFoundException.class, () -> deleteUserUseCase.execute(userId));
    }

    @Test
    void should_disableUser_when_userIdExists() {
        UUID userId = UUID.randomUUID();

        when(userRepository.existsByIdAndDisabledFalse(userId)).thenReturn(true);
        when(userRepository.getById(userId)).thenReturn(user);

        deleteUserUseCase.execute(userId);

        assertTrue(user.isDisabled());

        verify(domainEventPublisher, times(1)).publishUserDisabledEvent(new UserDisabledEvent(userId));
        verify(userRepository, times(1)).save(user);
    }
}
