package com.gft.user.application.user;

import com.gft.user.application.user.dto.UserRequest;
import com.gft.user.domain.factory.PasswordFactory;
import com.gft.user.domain.model.user.Email;
import com.gft.user.domain.model.user.Password;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
}
