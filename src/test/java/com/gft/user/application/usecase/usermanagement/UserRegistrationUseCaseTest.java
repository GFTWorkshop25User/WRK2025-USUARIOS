package com.gft.user.application.usecase.usermanagement;

import com.gft.user.application.dto.UserRequest;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.EmailAlreadyRegisteredException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserRegistrationUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRegistrationUseCase userRegistrationUseCase;

    @Test
    void should_register_when_userRequestIsValid() {
        UserRequest userRequest = new UserRequest("Manolo", "manolo@mail.com", "Manolito12345!");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        userRegistrationUseCase.execute(userRequest);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("Manolo", capturedUser.getName());
        assertEquals("manolo@mail.com", capturedUser.getEmail().value());
    }

    @Test
    void should_throwException_when_emailAlreadyInUse() {
        UserRequest userRequest = new UserRequest("Manolo", "manolo@mail.com", "Manolito12345!");
        when(userRepository.existsByEmail(userRequest.email())).thenReturn(true);

        assertThrows(EmailAlreadyRegisteredException.class, () -> userRegistrationUseCase.execute(userRequest) );

    }

}
