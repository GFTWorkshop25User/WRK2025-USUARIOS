package com.gft.user.application.user.management;

import com.gft.user.domain.model.user.Email;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangeEmailUseCase {

    private final UserRepository userRepository;

    public ChangeEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID userId, String newEmail) {
        if(newEmail.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if(!userRepository.existsByIdAndDisabledFalse(userId)) {
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = userRepository.getById(userId);

        Email email = new Email(newEmail);
        user.changeEmail(email);

        userRepository.save(user);
    }
}
