package com.gft.user.application.usecase.favorites;

import com.gft.user.application.usecase.BaseUseCase;
import com.gft.user.domain.model.user.FavoriteId;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddUserFavoriteProductUseCase extends BaseUseCase {

    private final Logger logger = LoggerFactory.getLogger(AddUserFavoriteProductUseCase.class);

    public AddUserFavoriteProductUseCase(UserRepository userRepository) {
        super(userRepository);
    }

    @Transactional
    public void execute(UUID userId, Long productId) {

        checkIfProductIdIsNull(productId);
        throwIfUserDoesntExistOrIsDisabled(userId);

        User user = userRepository.getById(userId);
        user.addFavoriteProduct(new FavoriteId(productId));
        userRepository.save(user);
        logger.info("Added favorite product [{}] to user [{}]", productId, userId);
    }

    private void checkIfProductIdIsNull(Long productId) {
        if(productId == null) {
            logger.warn("Tried to add a favorite product with a null product id");
            throw new IllegalArgumentException("Product id cannot be null");
        }
    }
}
