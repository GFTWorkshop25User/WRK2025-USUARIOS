package com.gft.user.infrastructure.messaging;

import com.gft.user.application.usecase.loyalty.IncrementLoyaltyPointsUseCase;
import com.gft.user.infrastructure.dto.UserIncrementLoyaltyPointsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQOrderConsumer {

    Logger logger = LoggerFactory.getLogger(RabbitMQNotificationConsumer.class);
    private final IncrementLoyaltyPointsUseCase incrementLoyaltyPointsUseCase;

    public RabbitMQOrderConsumer(IncrementLoyaltyPointsUseCase incrementLoyaltyPointsUseCase) {
        this.incrementLoyaltyPointsUseCase = incrementLoyaltyPointsUseCase;
    }

    @RabbitListener(queues = "coms.order")
    public void receiveNotification(UserIncrementLoyaltyPointsDto userIncrementLoyaltyPointsDto) {
        logger.info("Received notification: {}", userIncrementLoyaltyPointsDto);
        incrementLoyaltyPointsUseCase.execute(userIncrementLoyaltyPointsDto.userId(), userIncrementLoyaltyPointsDto.loyaltyPoints());
    }
}
