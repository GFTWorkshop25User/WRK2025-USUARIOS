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
public class ChangeUserNameUseCase {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(ChangeUserNameUseCase.class);

    public ChangeUserNameUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID userId, String newUserName) {
        if(!this.userRepository.existsByIdAndDisabledFalse(userId)) {
            logger.warn("Tried to change name to a disabled or non-existing user [{}]", userId);
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = this.userRepository.getById(userId);
        String oldName = user.getName();
        user.changeName(newUserName);

        logger.info("Changed user name [{}] to [{}] to user [{}]", oldName, newUserName, userId);
        this.userRepository.save(user);
    }
}
