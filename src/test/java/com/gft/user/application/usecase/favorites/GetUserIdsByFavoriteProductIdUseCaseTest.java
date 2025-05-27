package com.gft.user.application.usecase.favorites;

import com.gft.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserIdsByFavoriteProductIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserIdsByFavoriteProductIdUseCase getUserIdsByFavoriteProductIdUseCase;

    private final Long productId = 4L;

    @Test
    void should_returnUserIds_when_productIdExists() {
        List<UUID> userIdsGiven = List.of(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
                );

        when(userRepository.findAllUsersByProductId(4L)).thenReturn(userIdsGiven);
        List<UUID> userIdsReceived = getUserIdsByFavoriteProductIdUseCase.execute(productId);

        assertEquals(userIdsReceived.size(), userIdsGiven.size());
        assertEquals(userIdsGiven, userIdsReceived);
    }

    @Test
    void should_returnEmptyList_when_productIdDoesNotExists() {
        List<UUID> userIdsGiven = new ArrayList<>();

        when(userRepository.findAllUsersByProductId(4L)).thenReturn(userIdsGiven);
        List<UUID> userIdsReceived = getUserIdsByFavoriteProductIdUseCase.execute(productId);

        assertTrue(userIdsReceived.isEmpty());
        assertEquals(userIdsGiven, userIdsReceived);
    }

}
