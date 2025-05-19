package com.gft.user.application.user.favorites;

import com.gft.user.domain.model.user.FavoriteId;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetUserFavoriteProductsUseCase {
    private final UserRepository userRepository;

    public GetUserFavoriteProductsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Set<Long> execute(UUID userId) {
        if(!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        return userRepository.getById(userId).getFavoriteProductIds().stream()
                .map(FavoriteId::value)
                .collect(Collectors.toSet());
    }
}
