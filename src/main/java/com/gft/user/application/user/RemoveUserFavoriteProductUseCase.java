package com.gft.user.application.user;

import com.gft.user.domain.model.user.FavoriteId;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RemoveUserFavoriteProductUseCase {

    private final UserRepository userRepository;

    public RemoveUserFavoriteProductUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID userId, Long productId) {
        if(!this.userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = this.userRepository.getById(userId);
        user.removeFavorite(new FavoriteId(productId));

        this.userRepository.save(user);
    }
}
