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
public class RemoveUserFavoriteProductUseCase extends BaseUseCase {

    private final Logger logger = LoggerFactory.getLogger(RemoveUserFavoriteProductUseCase.class);

    public RemoveUserFavoriteProductUseCase(UserRepository userRepository) {
        super(userRepository);
    }

    @Transactional
    public void execute(UUID userId, Long productId) {

        throwIfUserDoesntExistOrIsDisabled(userId);

        User user = this.userRepository.getById(userId);
        user.removeFavorite(new FavoriteId(productId));
        this.userRepository.save(user);
        logger.info("Removed favorite product [{}] to user [{}]", productId, userId);
    }
}
