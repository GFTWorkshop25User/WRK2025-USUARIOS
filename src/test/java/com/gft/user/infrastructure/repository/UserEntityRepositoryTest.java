package com.gft.user.infrastructure.repository;

import com.gft.user.domain.model.user.*;
import com.gft.user.infrastructure.mapper.UserMapper;
import com.gft.user.infrastructure.entity.AddressEntity;
import com.gft.user.infrastructure.entity.UserEntity;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import com.gft.user.infrastructure.repository.jpa.JpaUserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserEntityRepositoryTest {

    @Mock
    private JpaUserEntityRepository jpaUserEntityRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    UserEntityRepository userEntityRepository;

    @Test
    void should_saveUser() {
        UUID uuid = UUID.randomUUID();

        UserEntity userEntity = new UserEntity(
                uuid,
                "Miguel",
                "miguel@gmail.com",
                "Pepito123!!!!!!!!",
                new AddressEntity("Spain", "241852", "Villalba", "Calle los floriponcios"),
                new HashSet<>(),
                0,
                false
        );

        User user = User.create(
                UserId.create(uuid),
                "Miguel",
                new Email("miguel@gmail.com"),
                new Password("Pepito123!!!!!!!!"),
                new Address("Spain", "241852", "Villalba", "Calle los floriponcios"),
                new HashSet<>(),
                new LoyaltyPoints(0),
                false
        );

        when(userMapper.toUserEntity(user)).thenReturn(userEntity);

        when(jpaUserEntityRepository.save(userEntity)).thenReturn(userEntity);

        UUID uuidReturned = userEntityRepository.save(user);

        assertEquals(uuidReturned, uuid);

    }

    @Test
    void should_getUserById() {
        UUID uuid = UUID.randomUUID();
        SafeString safeString = new SafeString("Pepito123!!!!!!!!");

        User user = User.create(
                UserId.create(uuid),
                "Miguel",
                new Email("miguel@gmail.com"),
                new Password(safeString),
                new Address("Spain", "241852", "Villalba", "Calle los floriponcios"),
                new HashSet<>(),
                new LoyaltyPoints(0),
                false
        );

        UserEntity userEntity = new UserEntity(
                uuid,
                "Miguel",
                "miguel@gmail.com",
                "Pepito123!!!!!!!!",
                new AddressEntity("Spain", "241852", "Villalba", "Calle los floriponcios"),
                new HashSet<>(),
                0,
                false
        );

        when(jpaUserEntityRepository.findById(uuid)).thenReturn(Optional.of(userEntity));
        when(userMapper.fromUserEntity(userEntity)).thenReturn(user);

        User obtainerUser = userEntityRepository.getById(uuid);

        assertEquals(obtainerUser.getId().getUuid(), userEntity.getId());
        assertEquals(obtainerUser.getEmail().value(), userEntity.getEmail());
        assertEquals(obtainerUser.getPassword().getHashedValue(), userEntity.getHashedPassword());
        assertEquals(obtainerUser.getAddress().city(), userEntity.getAddress().getCity());
        assertEquals(obtainerUser.getAddress().country(), userEntity.getAddress().getCountry());
        assertEquals(obtainerUser.getAddress().street(), userEntity.getAddress().getStreet());
        assertEquals(obtainerUser.getAddress().zipCode(), userEntity.getAddress().getZipCode());
        assertEquals(obtainerUser.getLoyaltyPoints().getValue(), userEntity.getLoyaltyPoints());
    }

    @Test
    void should_throwUserNotFoundException_when_userNotFound() {
        UUID uuid = UUID.randomUUID();

        when(jpaUserEntityRepository.findById(uuid)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> userEntityRepository.getById(uuid));

        assertEquals("User with id " + uuid + " not found", exception.getMessage());

    }

    @Test
    void should_returnTrue_when_userExists() {
        UUID uuid = UUID.randomUUID();

        when(jpaUserEntityRepository.existsById(uuid)).thenReturn(true);

        assertTrue(userEntityRepository.existsById(uuid));
    }
}
