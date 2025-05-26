package com.gft.user.infrastructure.messaging;

import com.gft.user.common.FeatureFlags;
import com.gft.user.domain.event.DomainEventPublisher;
import com.gft.user.domain.event.UserDisabledEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEventPublisher implements DomainEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final FeatureFlags featureFlags;

    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate, FeatureFlags featureFlags) {
        this.rabbitTemplate = rabbitTemplate;
        this.featureFlags = featureFlags;
    }

    public void publishUserDisabledEvent(UserDisabledEvent event) {
        if (!this.featureFlags.isToggleNotifications()) {
            return;
        }

        final String exchange = "user";
        final String routingKey = "disabled";
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
