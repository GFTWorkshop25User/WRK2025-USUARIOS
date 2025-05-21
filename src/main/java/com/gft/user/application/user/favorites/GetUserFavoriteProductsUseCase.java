package com.gft.user.application.user.favorites;

import com.gft.user.domain.model.user.FavoriteId;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetUserFavoriteProductsUseCase {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(GetUserFavoriteProductsUseCase.class);


    public GetUserFavoriteProductsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Set<Long> execute(UUID userId) {
        if(!userRepository.existsByIdAndDisabledFalse(userId)) {
            logger.warn("Tried to get favorite products from a non-existent user");
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        return userRepository.getById(userId).getFavoriteProductIds().stream()
                .map(FavoriteId::value)
                .collect(Collectors.toSet());
    }
}
