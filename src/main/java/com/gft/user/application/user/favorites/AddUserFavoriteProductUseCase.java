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
public class AddUserFavoriteProductUseCase {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(AddUserFavoriteProductUseCase.class);

    public AddUserFavoriteProductUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID userId, Long productId) {
        if(productId == null) {
            logger.warn("Tried to add a favorite product with a null product id");
            throw new IllegalArgumentException("Product id cannot be null");
        }

        if(!userRepository.existsByIdAndDisabledFalse(userId)) {
            logger.warn("Tried to add a favorite product to a non-existent user [{}]", userId);
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = userRepository.getById(userId);
        user.addFavoriteProduct(new FavoriteId(productId));
        userRepository.save(user);
        logger.info("Added favorite product [{}] to user [{}]", productId, userId);
    }
}
