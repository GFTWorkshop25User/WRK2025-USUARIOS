package com.gft.user.domain.event;

public interface DomainEventPublisher {

    void publishUserDisabledEvent(UserDisabledEvent event);
}
