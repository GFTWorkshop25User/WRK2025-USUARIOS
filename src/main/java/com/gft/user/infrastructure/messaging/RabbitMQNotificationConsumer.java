package com.gft.user.infrastructure.messaging;

import com.gft.user.infrastructure.dto.NotificationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQNotificationConsumer {

    Logger logger = LoggerFactory.getLogger(RabbitMQNotificationConsumer.class);

    @RabbitListener(queues = "coms.notification")
    public void receiveNotification(NotificationResponse notificationResponse) {
        logger.info("Received notification from user [{}]: {}", notificationResponse.userId(), notificationResponse.message());
    }
}
