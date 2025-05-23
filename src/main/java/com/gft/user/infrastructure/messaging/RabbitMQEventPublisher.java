package com.gft.user.infrastructure.messaging;

import com.gft.user.domain.event.DomainEventPublisher;
import com.gft.user.domain.event.UserDisabledEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class RabbitMQEventPublisher implements DomainEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserDisabledEvent(UserDisabledEvent event) {
        final String exchange = "user";
        final String routingKey = "disabled";
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
