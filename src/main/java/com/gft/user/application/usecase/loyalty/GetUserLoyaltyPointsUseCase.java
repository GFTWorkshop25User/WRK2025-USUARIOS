package com.gft.user.application.usecase.loyalty;

import com.gft.user.application.usecase.BaseUseCase;
import com.gft.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserLoyaltyPointsUseCase extends BaseUseCase {

    public GetUserLoyaltyPointsUseCase(UserRepository userRepository) {
        super(userRepository);
    }

    public int execute(UUID userId){

        throwIfUserDoesntExistOrIsDisabled(userId);

        return userRepository.getById(userId).getLoyaltyPoints().getValue();
    }
}
