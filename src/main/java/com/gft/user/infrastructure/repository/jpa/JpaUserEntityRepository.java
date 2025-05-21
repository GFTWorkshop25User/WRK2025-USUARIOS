package com.gft.user.infrastructure.repository.jpa;

import com.gft.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaUserEntityRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByIdAndDisabledFalse(UUID id);

    boolean existsByEmail(String email);

    @Query("SELECT u.id FROM UserEntity u WHERE :productId MEMBER OF u.favoriteProductIds")
    List<UUID> findUserIdsByFavoriteProductId(Long productId);
}
