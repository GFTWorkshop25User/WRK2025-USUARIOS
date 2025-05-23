package com.gft.user.application.usecase;

import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public abstract class BaseUseCase {

    protected final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(BaseUseCase.class);

    protected BaseUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void throwIfUserDoesntExistOrIsDisabled(UUID userId) {
        if(!userRepository.existsByIdAndDisabledFalse(userId)) {
            logger.warn("Tried to add a favorite product to a non-existent user [{}]", userId);
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }
    }
}
