package com.gft.user.infrastructure.messaging;

import com.gft.user.domain.event.UserDisabledEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class RabbitMQEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendUserDisabledMessage(UserDisabledEvent event) {
        final String exchange = "user";
        final String routingKey = "disabled";
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
