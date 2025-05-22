package com.gft.user.infrastructure.messaging;

import com.gft.user.domain.event.UserDisabledEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RabbitMQEventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQEventPublisher eventPublisher;

    @Test
    void should_publishEvent_when_sendUserDisabledMessage() {
        UserDisabledEvent userDisabledEvent = new UserDisabledEvent(UUID.randomUUID());

        eventPublisher.publishUserDisabledEvent(userDisabledEvent);

        verify(rabbitTemplate, times(1)).convertAndSend("user", "disabled", userDisabledEvent);
    }
}
