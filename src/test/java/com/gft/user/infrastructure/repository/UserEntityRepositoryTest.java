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

import java.util.*;

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
                "$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe",
                new AddressEntity("Spain", "241852", "Villalba", "Calle los floriponcios"),
                new HashSet<>(),
                0,
                false
        );

        User user = User.create(
                UserId.create(uuid),
                "Miguel",
                new Email("miguel@gmail.com"),
                Password.createPasswordFromHashed("$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe"),
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

        User user = User.create(
                UserId.create(uuid),
                "Miguel",
                new Email("miguel@gmail.com"),
                Password.createPasswordFromHashed("$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe"),
                new Address("Spain", "241852", "Villalba", "Calle los floriponcios"),
                new HashSet<>(),
                new LoyaltyPoints(0),
                false
        );

        UserEntity userEntity = new UserEntity(
                uuid,
                "Miguel",
                "miguel@gmail.com",
                "$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe",
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

    @Test
    void should_returnTrue_when_userExistsAndIsNotDisabled() {
        UUID uuid = UUID.randomUUID();

        when(jpaUserEntityRepository.existsByIdAndDisabledFalse(uuid)).thenReturn(true);

        assertTrue(userEntityRepository.existsByIdAndDisabledFalse(uuid));
    }

    @Test
    void should_returnTrue_when_emailExists() {
        String email = "enrique@gmail.com";
        when(jpaUserEntityRepository.existsByEmail(email)).thenReturn(true);

        assertTrue(userEntityRepository.existsByEmail(email));
    }

    @Test
    void should_returnFalse_when_emailDoesNotExists() {
        String email = "enrique@gmail.com";
        when(jpaUserEntityRepository.existsByEmail(email)).thenReturn(false);

        assertFalse(userEntityRepository.existsByEmail(email));
    }

    @Test
    void should_returnUserIds_when_productIdExistsInAnyOfThem() {
        List<UUID> userIdsGiven = List.of(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        );
        List<UUID> userIdsReturned;
        Long productId = 1L;

        when(jpaUserEntityRepository.findUserIdsByFavoriteProductId(productId)).thenReturn(userIdsGiven);

        userIdsReturned = userEntityRepository.findAllUsersByProductId(productId);

        assertEquals(userIdsGiven.size(), userIdsReturned.size());
        assertEquals(userIdsGiven.get(0), userIdsReturned.get(0));
    }

    @Test
    void should_returnUserIdsEmpty_when_productIdDoesNotExistsInAnyOfThem() {
        List<UUID> userIdsGiven = new ArrayList<>();
        List<UUID> userIdsReturned;
        Long productId = 1L;

        when(jpaUserEntityRepository.findUserIdsByFavoriteProductId(productId)).thenReturn(userIdsGiven);

        userIdsReturned = userEntityRepository.findAllUsersByProductId(productId);

        assertEquals(userIdsGiven.size(), userIdsReturned.size());
        assertTrue(userIdsGiven.isEmpty());
    }


}
