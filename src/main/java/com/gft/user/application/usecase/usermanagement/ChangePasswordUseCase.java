package com.gft.user.application.usecase.usermanagement;

import com.gft.user.application.usecase.BaseUseCase;
import com.gft.user.domain.factory.PasswordFactory;
import com.gft.user.domain.model.user.Password;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangePasswordUseCase extends BaseUseCase {

    private final Logger logger = LoggerFactory.getLogger(ChangePasswordUseCase.class);

    public ChangePasswordUseCase(UserRepository userRepository) {
        super(userRepository);
    }

    @Transactional
    public void execute(UUID userId, String oldPlainPassword, String newPlainPassword) {

        throwIfUserDoesntExistOrIsDisabled(userId);
        throwIfNewPasswordIsNull(userId, newPlainPassword);
        throwIfOldPasswordAndNewPasswordAreEqual(userId, oldPlainPassword, newPlainPassword);

        User user = userRepository.getById(userId);
        Password oldPassword = user.getPassword();

        throwIfOldPasswordsDoNotMatch(userId, oldPassword, oldPlainPassword);

        PasswordFactory passwordFactory = new PasswordFactory();
        Password newPassword = passwordFactory.createFromPlainText(newPlainPassword);

        user.changePassword(newPassword);
        userRepository.save(user);
        logger.info("Successfully changed password from user [{}]", userId);
    }

    private void throwIfNewPasswordIsNull(UUID userId, String newPlainPassword) {
        if (newPlainPassword == null) {
            logger.warn("Tried to change password from user [{}] to a null password", userId);
            throw new IllegalArgumentException("The new password cannot be null.");
        }
    }

    private void throwIfOldPasswordAndNewPasswordAreEqual(UUID userId, String oldPlainPassword, String newPlainPassword) {
        if (oldPlainPassword.equals(newPlainPassword)) {
            logger.warn("Tried to change password from user [{}] to a the same password", userId);
            throw new IllegalArgumentException("The new password cannot be the same as the old password.");
        }
    }

    private void throwIfOldPasswordsDoNotMatch(UUID userId, Password oldPassword, String oldPlainPassword) {
        if (!oldPassword.checkPassword(oldPlainPassword)){
            logger.warn("Tried to change password from user [{}] with an incorrect old password", userId);
            throw new IllegalArgumentException("The old password does not match.");
        }
    }
}
