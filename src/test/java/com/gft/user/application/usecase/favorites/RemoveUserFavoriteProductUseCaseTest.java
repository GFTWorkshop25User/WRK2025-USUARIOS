package com.gft.user.application.usecase.favorites;

import com.gft.user.domain.model.user.*;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemoveUserFavoriteProductUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RemoveUserFavoriteProductUseCase removeUserFavoriteProductUseCase;

    private final UUID userId = UUID.randomUUID();

    private final User user = User.create(
            UserId.create(userId),
            "Alfonso Gutierrez",
            new Email("alfonsito@gmail.com"),
            Password.createPasswordFromHashed("$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe!!"),
            new Address("","","",""),
            new HashSet<>(Set.of(new FavoriteId(4L), new FavoriteId(5L))),
            new LoyaltyPoints(0),
            false
    );

    @Test
    void should_throwException_when_userIdDoesNotExist() {
        when(userRepository.existsByIdAndDisabledFalse(userId)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> removeUserFavoriteProductUseCase.execute(userId, 4L));
    }

    @Test
    void should_disableUser_when_userIdExists() {
        when(userRepository.existsByIdAndDisabledFalse(userId)).thenReturn(true);
        when(userRepository.getById(userId)).thenReturn(user);

        removeUserFavoriteProductUseCase.execute(userId, 4L);

        assertEquals(1, user.getFavoriteProductIds().size());
    }
}
