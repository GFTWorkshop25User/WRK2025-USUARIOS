package com.gft.user.infrastructure.repository;

import com.gft.user.application.user.dto.UserRequest;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.entity.UserEntity;
import com.gft.user.infrastructure.mapper.UserMapper;
import com.gft.user.infrastructure.repository.jpa.JpaUserEntityRepository;
import lombok.Generated;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@Generated
@Repository
public class UserEntityRepository implements UserRepository {

    private final JpaUserEntityRepository jpaUserEntityRepository;
    private final UserMapper userMapper;

    public UserEntityRepository(JpaUserEntityRepository jpaUserEntityRepository, UserMapper userMapper) {
        this.jpaUserEntityRepository = jpaUserEntityRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UUID save(User user) {
        return jpaUserEntityRepository.save(userMapper.toUserEntity(user)).getId();
    }
}
