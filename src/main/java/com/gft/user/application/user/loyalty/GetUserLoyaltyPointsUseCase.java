package com.gft.user.application.user.loyalty;

import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
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

        User user = userRepository.getById(uuid);

        return user.getLoyaltyPoints().getValue();
    }
}
