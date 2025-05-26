package com.gft.user.application.usecase.usermanagement;

import com.gft.user.application.usecase.BaseUseCase;
import com.gft.user.domain.model.user.Email;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.EmailAlreadyRegisteredException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangeEmailUseCase extends BaseUseCase {

    private final Logger logger = LoggerFactory.getLogger(ChangeEmailUseCase.class);

    public ChangeEmailUseCase(UserRepository userRepository) {
        super(userRepository);
    }

    @Transactional
    public void execute(UUID userId, String newEmail) {

        throwIfEmailIsNotValid(userId, newEmail);
        throwIfUserDoesntExistOrIsDisabled(userId);

        User user = userRepository.getById(userId);

        Email email = new Email(newEmail);
        String oldEmail = user.getEmail().value();
        user.changeEmail(email);

        userRepository.save(user);
        logger.info("Email from user [{}] changed from [{}] to [{}]", userId, oldEmail, newEmail);
    }

    private void throwIfEmailIsNotValid(UUID userId, String email) {
        if(email.isBlank()) {
            logger.warn("Tried to change to a blank email");
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (userRepository.existsByEmail(email)){
            logger.warn("Tried to change email from user [{}] to an existing email [{}]", userId, email);
            throw new EmailAlreadyRegisteredException(String.format("Email %s is already being used", email));
        }
    }
}
