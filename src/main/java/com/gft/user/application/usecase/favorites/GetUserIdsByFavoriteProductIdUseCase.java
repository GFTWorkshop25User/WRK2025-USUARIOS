package com.gft.user.application.usecase.favorites;

import com.gft.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetUserIdsByFavoriteProductIdUseCase {

    private final UserRepository userRepository;

    public GetUserIdsByFavoriteProductIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UUID> execute(Long productId) {
        return userRepository.findAllUsersByProductId(productId);
    }

}
