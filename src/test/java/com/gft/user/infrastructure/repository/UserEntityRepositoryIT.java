package com.gft.user.infrastructure.repository;

import com.gft.user.domain.model.user.Email;
import com.gft.user.domain.model.user.Password;
import com.gft.user.domain.model.user.User;
import com.gft.user.infrastructure.entity.UserEntity;
import com.gft.user.infrastructure.mapper.UserMapper;
import com.gft.user.infrastructure.repository.jpa.JpaUserEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@Sql(scripts= {"/data/h2/schema_testing.sql", "/data/h2/data_testing.sql"})
class UserEntityRepositoryIT {

    @Autowired
    private JpaUserEntityRepository jpaUserEntityRepository;

    @MockitoBean
    private UserMapper userMapper;

    private UserEntityRepository userEntityRepository;

    @BeforeEach
    void setUp() {
        userEntityRepository = new UserEntityRepository(jpaUserEntityRepository, userMapper);
    }

    @Test
    void should_saveUser_when_userIsValid() {
        UUID userId = UUID.randomUUID();
        User user = User.register("Manolo", new Email("manolo@mail.com"), Password.createPasswordFromPlain("Manolito12345!"));
        UserEntity userEntity = new UserEntity(
                userId,
                "Manolo",
                "manolo@mail.com",
                "pass",
                null,
                new HashSet<>(),
                0,
                false
        );

        when(userMapper.toUserEntity(user)).thenReturn(userEntity);
        when(userMapper.fromUserEntity(userEntity)).thenReturn(user);

        userEntityRepository.save(user);

        assertTrue(userEntityRepository.existsByEmail("manolo@mail.com"));
        assertTrue(userEntityRepository.existsByIdAndDisabledFalse(userId));
        assertEquals(user, userEntityRepository.getById(userId));
    }

    @Test
    void should_returnFalse_when_existsByDisabledFalse() {
        assertFalse(jpaUserEntityRepository.existsByIdAndDisabledFalse(UUID.fromString("fe957011-1565-4e7f-9e3c-0e8f7d015ef3")));
    }
}
