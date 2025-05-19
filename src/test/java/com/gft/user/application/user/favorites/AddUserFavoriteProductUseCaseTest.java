package com.gft.user.application.user.favorites;


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
class AddUserFavoriteProductUseCaseTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AddUserFavoriteProductUseCase addUserFavoriteProductUseCase;

    private final UUID userId = UUID.randomUUID();
    private final User user = User.create(
            UserId.create(userId),
            "Alfonso Gutierrez",
            new Email("alfonsito@gmail.com"),
            Password.createPasswordFromPlain("Pepito1234!!"),
            new Address("","","",""),
            new HashSet<>(Set.of(new FavoriteId(4L),new FavoriteId(6L))),
            new LoyaltyPoints(0),
            false
    );

    @Test
    void should_addUserFavoriteProduct_whenUserExists() {
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.getById(userId)).thenReturn(user);

        addUserFavoriteProductUseCase.execute(user.getId().getUuid(), 5L);

        assertTrue(user.getFavoriteProductIds().contains(new FavoriteId(5L)));
    }

    @Test
    void should_throwIllegalArgumentException_when_productIdNull() {
        assertThrows(IllegalArgumentException.class, () -> addUserFavoriteProductUseCase.execute(user.getId().getUuid(), null));
    }

    @Test
    void should_throwUserNotFoundException_when_userDoesNotExist() {
        when(userRepository.existsById(userId)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> addUserFavoriteProductUseCase.execute(userId, 4L));
    }

}
