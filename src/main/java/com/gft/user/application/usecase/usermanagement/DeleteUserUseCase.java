package com.gft.user.application.usecase.usermanagement;

import com.gft.user.application.usecase.BaseUseCase;
import com.gft.user.domain.event.DomainEventPublisher;
import com.gft.user.domain.event.UserDisabledEvent;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserUseCase extends BaseUseCase {

    private final DomainEventPublisher domainEventPublisher;
    private final Logger logger = LoggerFactory.getLogger(DeleteUserUseCase.class);

    public DeleteUserUseCase(UserRepository userRepository, DomainEventPublisher domainEventPublisher) {
        super(userRepository);
        this.domainEventPublisher = domainEventPublisher;
    }

    @Transactional
    public void execute(UUID userId) {

        throwIfUserDoesntExistOrIsDisabled(userId);

        User user = this.userRepository.getById(userId);
        user.disableUser();

        logger.info("Disabled user [{}]", user);
        this.userRepository.save(user);
        domainEventPublisher.publishUserDisabledEvent(new UserDisabledEvent(userId));
    }
}
