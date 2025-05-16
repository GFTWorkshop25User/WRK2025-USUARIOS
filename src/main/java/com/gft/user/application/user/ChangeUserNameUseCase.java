package com.gft.user.application.user;

import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangeUserNameUseCase {

    private final UserRepository userRepository;

    public ChangeUserNameUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID userId, String newUserName) {
        if(!this.userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = this.userRepository.getById(userId);
        user.changeName(newUserName);

        this.userRepository.save(user);
    }
}
