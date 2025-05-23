package com.gft.user.application.usecase.loyalty;

import com.gft.user.application.usecase.BaseUseCase;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DecrementLoyaltyPointsUseCase extends BaseUseCase {

    private final Logger logger = LoggerFactory.getLogger(DecrementLoyaltyPointsUseCase.class);


    public DecrementLoyaltyPointsUseCase(UserRepository userRepository) {
        super(userRepository);
    }

    @Transactional
    public void execute(UUID userId, int decrementAmount) {

        throwIfUserDoesntExistOrIsDisabled(userId);

        User user = userRepository.getById(userId);
        user.decrementLoyaltyPoints(decrementAmount);

        userRepository.save(user);
        logger.info("Decremented [{}] loyalty points to user [{}]", decrementAmount, userId);
    }
}
