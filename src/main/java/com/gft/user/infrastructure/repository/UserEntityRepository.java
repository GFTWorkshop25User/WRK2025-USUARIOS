package com.gft.user.infrastructure.repository;

import com.gft.user.application.user.dto.UserRequest;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.entity.UserEntity;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import com.gft.user.infrastructure.mapper.UserMapper;
import com.gft.user.infrastructure.repository.jpa.JpaUserEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public User getById(UUID id) {
        Optional<UserEntity> optional = jpaUserEntityRepository.findById(id);

        if (optional.isEmpty()) {
            throw new UserNotFoundException(String.format("User with id %s not found", id));
        }

        return userMapper.fromUserEntity(optional.get());

    }

    @Override
    public boolean existsById(UUID id) { return jpaUserEntityRepository.existsById(id); }
}
