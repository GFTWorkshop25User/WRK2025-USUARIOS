package com.gft.user.domain.repository;

import com.gft.user.domain.model.user.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    UUID save(User user);

    User getById(UUID id);

    boolean existsById(UUID id);

    boolean existsByIdAndDisabledFalse(UUID id);

    boolean existsByEmail(String email);

    List<UUID> findAllUsersByProductId(Long productId);

}
