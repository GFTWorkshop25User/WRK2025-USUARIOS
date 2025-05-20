package com.gft.user.application.user.management;

import com.gft.user.domain.model.user.Email;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.EmailAlreadyRegisteredException;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangeEmailUseCase {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(ChangeEmailUseCase.class);

    public ChangeEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID userId, String newEmail) {
        if(newEmail.isBlank()) {
            logger.warn("Tried to change to a blank email");
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (userRepository.existsByEmail(newEmail)){
            logger.warn("Tried to change email from user [{}] to an existing email [{}]", userId, newEmail);
            throw new EmailAlreadyRegisteredException(String.format("Email %s is already being used", newEmail));
        }

        if(!userRepository.existsByIdAndDisabledFalse(userId)) {
            logger.warn("Tried to change email to a disabled or non-existing user [{}]", userId);
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = userRepository.getById(userId);

        Email email = new Email(newEmail);
        String oldEmail = user.getEmail().value();
        user.changeEmail(email);

        userRepository.save(user);
        logger.info("Email from user [{}] changed from [{}] to [{}]", userId, oldEmail, newEmail);
    }
}
