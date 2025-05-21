package com.gft.user.application.user.favorites;

import com.gft.user.domain.model.user.FavoriteId;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RemoveUserFavoriteProductUseCase {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(RemoveUserFavoriteProductUseCase.class);

    public RemoveUserFavoriteProductUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID userId, Long productId) {
        if(!this.userRepository.existsByIdAndDisabledFalse(userId)) {
            logger.warn("Tried to remove a favorite product to a non-existent user [{}]", userId);
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = this.userRepository.getById(userId);
        user.removeFavorite(new FavoriteId(productId));
        this.userRepository.save(user);
        logger.info("Removed favorite product [{}] to user [{}]", productId, userId);
    }
}
