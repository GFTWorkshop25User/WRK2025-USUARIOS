package com.gft.user.application.user;

import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserLoyaltyPointsUseCase {

    private final UserRepository userRepository;

    public GetUserLoyaltyPointsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int execute(UUID uuid){

        if (!userRepository.existsById(uuid)) {
            throw new UserNotFoundException(String.format("User with id %s not found", uuid));
        }

        return userRepository.getLoyaltyPoints(uuid);

    }

}
