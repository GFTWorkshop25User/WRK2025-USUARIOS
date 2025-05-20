package com.gft.user.application.user.loyalty;

import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IncrementLoyaltyPointsUseCase {

    private final UserRepository userRepository;

    public IncrementLoyaltyPointsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID userId, int incrementAmmount) {
        if(!userRepository.existsByIdAndDisabledFalse(userId)) {
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = userRepository.getById(userId);
        user.getLoyaltyPoints().increment(incrementAmmount);

        userRepository.save(user);
    }

}
