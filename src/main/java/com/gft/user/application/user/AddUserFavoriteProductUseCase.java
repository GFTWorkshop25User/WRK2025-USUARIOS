package com.gft.user.application.user;

import com.gft.user.domain.model.user.FavoriteId;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.domain.exception.ProductAlreadyInFavoritesException;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class AddUserFavoriteProductUseCase {

    private final UserRepository userRepository;

    public AddUserFavoriteProductUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID uuid, Long productId) {
        if(productId == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }

        if(!userRepository.existsById(uuid)) {
            throw new UserNotFoundException(String.format("User with id %s not found", uuid));
        }

        User user = userRepository.getById(uuid);
        user.addFavoriteProduct(new FavoriteId(productId));
        userRepository.save(user);
    }
}
