package com.gft.user.application.user.favorites;

import com.gft.user.domain.model.user.*;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
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
public class GetUserFavoriteProductsUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserFavoriteProductsUseCase getUserFavoriteProductsUseCase;

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

    @BeforeEach
    void setUp() {
        user.getFavoriteProductIds().add(new FavoriteId(2L));
    }

    @Test
    void should_throwException_when_userIdDoesNotExist() {
        when(userRepository.existsById(userId)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> getUserFavoriteProductsUseCase.execute(userId));
    }


    @Test
    void should_returnFavoriteIds_when_userIdIsValid() {
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.getById(userId)).thenReturn(user);

        assertEquals(getUserFavoriteProductsUseCase.execute(userId), user.getFavoriteProductIds());
    }

}
