package com.gft.user.application.user.management;

import com.gft.user.domain.factory.PasswordFactory;
import com.gft.user.domain.model.user.Password;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangePasswordUseCase {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(ChangePasswordUseCase.class);

    public ChangePasswordUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID uuid, String oldPlainPassword, String newPlainPassword) {

        if (!userRepository.existsByIdAndDisabledFalse(uuid)) {
            logger.warn("Tried to change password to a disabled or non-existing user [{}]", uuid);
            throw new UserNotFoundException(String.format("User with id %s not found", uuid));
        }

        User user = userRepository.getById(uuid);
        Password oldPassword = user.getPassword();

        if (!oldPassword.checkPassword(oldPlainPassword)){
            logger.warn("Tried to change password from user [{}] with an incorrect old password", uuid);
            throw new IllegalArgumentException("The old password does not match.");
        }

        if (newPlainPassword == null) {
            logger.warn("Tried to change password from user [{}] to a null password", uuid);
            throw new IllegalArgumentException("The new password cannot be null.");
        }

        if (oldPlainPassword.equals(newPlainPassword)) {
            logger.warn("Tried to change password from user [{}] to a the same password", uuid);
            throw new IllegalArgumentException("The new password cannot be the same as the old password.");
        }

        PasswordFactory passwordFactory = new PasswordFactory();
        Password newPassword = passwordFactory.createFromPlainText(newPlainPassword);

        user.changePassword(newPassword);
        userRepository.save(user);
        logger.info("Successfully changed password from user [{}]", uuid);
    }
}
