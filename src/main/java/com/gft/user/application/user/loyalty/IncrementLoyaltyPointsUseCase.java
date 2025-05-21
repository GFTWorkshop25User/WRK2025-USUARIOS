package com.gft.user.application.user.loyalty;

import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IncrementLoyaltyPointsUseCase {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(IncrementLoyaltyPointsUseCase.class);

    public IncrementLoyaltyPointsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID userId, int incrementAmount) {
        if(!userRepository.existsByIdAndDisabledFalse(userId)) {
            logger.warn("Tried to increment loyalty points to a non-existent user");
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = userRepository.getById(userId);
        user.incrementLoyaltyPoints(incrementAmount);

        userRepository.save(user);
        logger.info("Incremented [{}] loyalty points to user [{}]", incrementAmount, userId);
    }
}
