package com.gft.user.application.user;

import com.gft.user.domain.model.user.FavoriteId;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class GetFavoriteProductsUseCase {
    private final UserRepository userRepository;

    public GetFavoriteProductsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Set<FavoriteId> execute(UUID userId) {
        if(!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        return userRepository.getById(userId).getFavoriteProductIds();
    }
}
