package com.gft.user.application.usecase.usermanagement;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChangePasswordUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChangePasswordUseCase changePasswordUseCase;

    UUID uuid = UUID.randomUUID();
    String oldPassword = "String1234567!";
    String newPassword = "Josep1234567!";

    private final User user = User.create(
            UserId.create(uuid),
            "Alfonso Gutierrez",
            new Email("alfonsito@gmail.com"),
            Password.createPasswordFromPlain(oldPassword),
            new Address("","","",""),
            new HashSet<>(),
            new LoyaltyPoints(0),
            false
    );

    @Test
    void should_change_whenPasswordIsCorrect() {
        when(userRepository.getById(uuid)).thenReturn(user);
        when(userRepository.existsByIdAndDisabledFalse(uuid)).thenReturn(true);

        changePasswordUseCase.execute(uuid, oldPassword, newPassword);

        User actualUser = userRepository.getById(uuid);

        assertTrue(actualUser.getPassword().checkPassword(newPassword));
    }

    @Test
    void should_throwException_whenPasswordIsNotTheOld() {
        when(userRepository.getById(uuid)).thenReturn(user);
        when(userRepository.existsByIdAndDisabledFalse(uuid)).thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class, () -> changePasswordUseCase.execute(uuid, "PepeAntonioJose12345!", newPassword));
        assertEquals("The old password does not match.", exception.getMessage());
    }

    @Test
    void should_throwException_whenPasswordIsNull() {
        when(userRepository.existsByIdAndDisabledFalse(uuid)).thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class, () -> changePasswordUseCase.execute(uuid, oldPassword, null));
        assertEquals("The new password cannot be null.", exception.getMessage());
    }

    @Test
    void should_throwException_whenPasswordSameAsOld() {
        when(userRepository.existsByIdAndDisabledFalse(uuid)).thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class, () -> changePasswordUseCase.execute(uuid, oldPassword, oldPassword));
        assertEquals("The new password cannot be the same as the old password.", exception.getMessage());
    }

    @Test
    void should_throwException_whenUserNotFound() {
        when(userRepository.existsByIdAndDisabledFalse(uuid)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> changePasswordUseCase.execute(uuid, oldPassword, newPassword));
    }

}
