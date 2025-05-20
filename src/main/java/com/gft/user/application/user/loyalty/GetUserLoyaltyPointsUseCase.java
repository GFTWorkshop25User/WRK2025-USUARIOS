package com.gft.user.application.user.loyalty;

import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserLoyaltyPointsUseCase {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(GetUserLoyaltyPointsUseCase.class);

    public GetUserLoyaltyPointsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int execute(UUID userId){

        if (!userRepository.existsByIdAndDisabledFalse(userId)) {
            logger.warn("Tried to get loyalty points from a non-existent user");
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = userRepository.getById(userId);

        return user.getLoyaltyPoints().getValue();
    }
}
