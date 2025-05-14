package com.gft.user.infrastructure.repository.jpa;

import com.gft.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaUserEntityRepository extends JpaRepository<UserEntity, UUID> {

}
