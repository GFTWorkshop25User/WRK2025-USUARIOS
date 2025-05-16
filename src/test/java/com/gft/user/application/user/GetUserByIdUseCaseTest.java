package com.gft.user.application.user;

import com.gft.user.domain.model.user.*;
import com.gft.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class GetUserByIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserByIdUseCase getUserByIdUseCase;

    private final UUID uuid = UUID.randomUUID();

    private final User user = User.create(
            UserId.create(uuid),
            "Alfonso Gutierrez",
            new Email("alfonsito@gmail.com"),
            Password.createPasswordFromHashed("$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe!!"),
            new Address("","","",""),
            new HashSet<>(),
            new LoyaltyPoints(0),
            false
    );

    @Test
    void should_returnUser_when_userIdExists() {

        when(userRepository.getById(uuid)).thenReturn(user);

        User obtainedUser = getUserByIdUseCase.execute(uuid);

        assertEquals(obtainedUser.getId().getUuid(), user.getId().getUuid());
        assertEquals(obtainedUser.getEmail(), user.getEmail());
        assertEquals(obtainedUser.getPassword(), user.getPassword());
        assertEquals(obtainedUser.getAddress(), user.getAddress());
        assertEquals(obtainedUser.getLoyaltyPoints(), user.getLoyaltyPoints());

    }

}
