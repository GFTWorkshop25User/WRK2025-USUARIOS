package com.gft.user.infrastructure.repository.jpa;

import com.gft.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface JpaUserEntityRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT u.loyaltyPoints FROM UserEntity u WHERE u.id = :userId")
    int findLoyaltyPointsById(UUID userId);


}
