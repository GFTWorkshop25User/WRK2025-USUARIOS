package com.gft.user.application.user;

import com.gft.user.application.user.dto.UserRequest;
import com.gft.user.domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRegistrationUseCaseTest {

    @Autowired
    private UserRegistrationUseCase userRegistrationUseCase;

    @Test
    void should_register_when_userRequestIsValid() {

        UserRequest userRequest = new UserRequest("username", "username@mail.com", "Password123456!");
        UUID uuid = userRegistrationUseCase.execute(userRequest);

        assertNotNull(uuid);
    }
}
