package com.gft.user.application.usecase.favorites;

import com.gft.user.application.usecase.BaseUseCase;
import com.gft.user.domain.model.user.FavoriteId;
import com.gft.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetUserFavoriteProductsUseCase extends BaseUseCase {

    public GetUserFavoriteProductsUseCase(UserRepository userRepository) {
        super(userRepository);
    }

    public Set<Long> execute(UUID userId) {

        throwIfUserDoesntExistOrIsDisabled(userId);

        return userRepository.getById(userId).getFavoriteProductIds().stream()
                .map(FavoriteId::value)
                .collect(Collectors.toSet());
    }
}
