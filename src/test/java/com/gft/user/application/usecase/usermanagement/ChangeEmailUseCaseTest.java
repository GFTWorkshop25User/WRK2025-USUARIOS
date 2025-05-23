package com.gft.user.application.usecase.usermanagement;

import com.gft.user.domain.model.user.*;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.EmailAlreadyRegisteredException;
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
class ChangeEmailUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChangeEmailUseCase changeEmailUseCase;

    private final UUID userId = UUID.randomUUID();

    private final User user = User.create(
            UserId.create(userId),
            "Alfonso Gutierrez",
            new Email("alfonsito@gmail.com"),
            Password.createPasswordFromPlain("Pepito1234!!"),
            new Address("","","",""),
            new HashSet<>(),
            new LoyaltyPoints(0),
            false
    );

    @Test
    void should_throwUserNotFoundException_when_userDoesNotExist() {
        when(userRepository.existsByIdAndDisabledFalse(userId)).thenReturn(false);

        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> changeEmailUseCase.execute(userId, "gmail.com"));

        assertEquals(String.format("User with id %s not found", userId), userNotFoundException.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_emailIsEmpty() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> changeEmailUseCase.execute(userId, ""));

        assertEquals("Email cannot be empty", illegalArgumentException.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_emailIsNotValid() {
        when(userRepository.getById(userId)).thenReturn(user);
        when(userRepository.existsByIdAndDisabledFalse(userId)).thenReturn(true);

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> changeEmailUseCase.execute(userId, "mockito@test"));

        assertEquals("Email is not valid", illegalArgumentException.getMessage());
    }

    @Test
    void should_changeEmail_when_emailIsValid() {
        when(userRepository.getById(userId)).thenReturn(user);
        when(userRepository.existsByIdAndDisabledFalse(userId)).thenReturn(true);

        changeEmailUseCase.execute(userId, "mockito@test.com");

        assertEquals("mockito@test.com", user.getEmail().value());
    }

    @Test
    void should_throwException_whenEmailAlreadyExists() {
        when(userRepository.existsByEmail("pepe@json.com")).thenReturn(true);
        assertThrows(EmailAlreadyRegisteredException.class, () -> changeEmailUseCase.execute(UUID.randomUUID(), "pepe@json.com"));
    }
}
