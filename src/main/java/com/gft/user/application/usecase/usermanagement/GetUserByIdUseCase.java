package com.gft.user.application.usecase.usermanagement;

import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID userId) {
        return userRepository.getById(userId);
    }

}
