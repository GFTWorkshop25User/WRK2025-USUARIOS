package com.gft.user.application.user.management;

import com.gft.user.domain.factory.PasswordFactory;
import com.gft.user.domain.model.user.Password;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangePasswordUseCase {

    private final UserRepository userRepository;

    public ChangePasswordUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID uuid, String oldPlainPassword, String newPlainPassword) {

        if (!userRepository.existsById(uuid)) {
            throw new UserNotFoundException(String.format("User with id %s not found", uuid));
        }

        User user = userRepository.getById(uuid);
        Password oldPassword = user.getPassword();

        if (!oldPassword.checkPassword(oldPlainPassword)){
            throw new IllegalArgumentException("The old password does not match.");
        }

        if (newPlainPassword == null) {
            throw new IllegalArgumentException("The new password cannot be null.");
        }

        if (oldPlainPassword.equals(newPlainPassword)) {
            throw new IllegalArgumentException("The new password cannot be the same as the old password.");
        }

        PasswordFactory passwordFactory = new PasswordFactory();
        Password newPassword = passwordFactory.createFromPlainText(newPlainPassword);

        user.changePassword(newPassword);
        userRepository.save(user);


    }

}
