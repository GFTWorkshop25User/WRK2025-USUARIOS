package com.gft.user.infrastructure.mapper;

import com.gft.user.domain.factory.PasswordFactory;
import com.gft.user.domain.model.user.*;
import com.gft.user.infrastructure.entity.AddressEntity;
import com.gft.user.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private final AddressMapper addressMapper = new AddressMapper();
    private final UserMapper userMapper = new UserMapper(addressMapper);

    @Test
    void should_mapUser_to_userEntity() {
        PasswordFactory passwordFactory = new PasswordFactory();
        Password password = passwordFactory.createFromPlainText("APepito1234!");
        User user = User.create(
                new UserId(),
                "miguel",
                new Email("miguel@gmail.com"),
                password,
                new Address("Spain", "241852", "Villalba", "Calle los floriponcios"),
                new HashSet<>(),
                new LoyaltyPoints(0),
                false
        );

        UserEntity userEntity = userMapper.toUserEntity(user);

        assertEquals(user.getId().getUuid(), userEntity.getId());
        assertEquals(user.getName(), userEntity.getName());
        assertEquals(user.getEmail().value(), userEntity.getEmail());
        assertEquals(user.getPassword().getHashedValue(), userEntity.getHashedPassword());
        assertEquals(user.getAddress().city(), userEntity.getAddress().getCity());
        assertEquals(user.getAddress().country(), userEntity.getAddress().getCountry());
        assertEquals(user.getAddress().zipCode(), userEntity.getAddress().getZipCode());
        assertEquals(user.getAddress().street(), userEntity.getAddress().getStreet());
        assertEquals(user.getFavoriteProductIds().stream().map(FavoriteId::value).collect(Collectors.toSet()), userEntity.getFavoriteProductIds());
        assertEquals(user.getLoyaltyPoints().getValue(), userEntity.getLoyaltyPoints());
        assertEquals(user.isDisabled(), userEntity.isDisabled());
    }

    @Test
    void should_mapUserEntity_to_User(){

        UserEntity userEntity = new UserEntity(
                UUID.randomUUID(),
                "Miguel",
                "miguel@gmail.com",
                "123456!Pep",
                new AddressEntity("Spain", "241852", "Villalba", "Calle los floriponcios"),
                new HashSet<>(),
                0,
                false
        );

        User user = userMapper.fromUserEntity(userEntity);

        assertEquals(userEntity.getId(), user.getId().getUuid());
        assertEquals(userEntity.getName(), user.getName());
        assertEquals(userEntity.getEmail(), user.getEmail().value());
        assertEquals(userEntity.getHashedPassword(), user.getPassword().getHashedValue());
        assertEquals(userEntity.getAddress().getCity(), user.getAddress().city());
        assertEquals(userEntity.getAddress().getCountry(), user.getAddress().country());
        assertEquals(userEntity.getAddress().getZipCode(), user.getAddress().zipCode());
        assertEquals(userEntity.getAddress().getStreet(), user.getAddress().street());
        assertEquals(userEntity.getFavoriteProductIds(), user.getFavoriteProductIds().stream().map(FavoriteId::value).collect(Collectors.toSet()));
        assertEquals(userEntity.getLoyaltyPoints(), user.getLoyaltyPoints().getValue());
        assertEquals(userEntity.isDisabled(), user.isDisabled());

    }

}
