package com.gft.user.infrastructure.repository;

import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.repository.jpa.JpaUserEntityRepository;
import lombok.Generated;

@Generated
public class UserEntityRepository implements UserRepository {

    private final JpaUserEntityRepository jpaUserEntityRepository;

    public UserEntityRepository(JpaUserEntityRepository jpaUserEntityRepository) {
        this.jpaUserEntityRepository = jpaUserEntityRepository;
    }
}
