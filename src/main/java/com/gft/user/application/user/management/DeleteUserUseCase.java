package com.gft.user.application.user.management;

import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserUseCase {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(DeleteUserUseCase.class);

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID userId) {
        if(!this.userRepository.existsByIdAndDisabledFalse(userId)) {
            logger.warn("Tried to delete a disabled or non-existing user [{}]", userId);
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = this.userRepository.getById(userId);
        user.disableUser();

        logger.info("Disabled user [{}]", user);
        this.userRepository.save(user);
    }
}
