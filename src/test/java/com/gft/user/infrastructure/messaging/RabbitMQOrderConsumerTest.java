package com.gft.user.infrastructure.messaging;

import com.gft.user.application.usecase.loyalty.IncrementLoyaltyPointsUseCase;
import com.gft.user.infrastructure.dto.UserIncrementLoyaltyPointsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RabbitMQOrderConsumerTest {

    @Mock
    private IncrementLoyaltyPointsUseCase incrementLoyaltyPointsUseCase;

    @InjectMocks
    private RabbitMQOrderConsumer rabbitMQOrderConsumer;

    @Test
    void should_executeIncrementLoyaltyPointsUseCase_when_receiveNotification() {
        UUID userId = UUID.randomUUID();
        doNothing().when(incrementLoyaltyPointsUseCase).execute(userId, 10);

        UserIncrementLoyaltyPointsDto userIncrementLoyaltyPointsDto = new UserIncrementLoyaltyPointsDto(userId, 10);

        rabbitMQOrderConsumer.receiveNotification(userIncrementLoyaltyPointsDto);

        verify(incrementLoyaltyPointsUseCase, times(1)).execute(userId, 10);
    }
}
