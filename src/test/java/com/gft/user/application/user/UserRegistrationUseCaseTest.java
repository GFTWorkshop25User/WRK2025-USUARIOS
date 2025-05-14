package com.gft.user.application.user;

import com.gft.user.application.user.dto.UserRequest;
import com.gft.user.domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRegistrationUseCaseTest {

    @Autowired
    private UserRegistrationUseCase userRegistrationUseCase;

    @Test
    public void should_register_when_userRequestIsValid() {

        UserRequest userRequest = new UserRequest("username", "username@mail.com", "Password123456!");
        User user = userRegistrationUseCase.execute(userRequest);

        assertNotNull(user);
        assertEquals("username@mail.com", user.getEmail().value());
        assertNotEquals("Password123456!", user.getPassword().getHashedValue());
        assertTrue(user.getPassword().checkPassword("Password123456!"));
    }
}
