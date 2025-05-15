package com.gft.user.infrastructure.mapper;

import com.gft.user.application.user.UserRegistrationUseCase;
import com.gft.user.domain.factory.PasswordFactory;
import com.gft.user.domain.model.user.*;
import com.gft.user.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void should_map_user_to_userEntity() {
        PasswordFactory passwordFactory = new PasswordFactory();
        Password password = passwordFactory.createFromPlainText("APepito1234!");
        User user = User.create(
                new UserId(),
                "miguel",
                new Email("miguel@gmail.com"),
                password,
                new Address("Spain", "241852", "Villalba", "Calle los floriponcios"),
                new HashSet<>(),
                new LoyaltyPoints(0)
        );

        UserEntity userEntity = userMapper.toUserEntity(user);

        assertEquals(user.getId().getUuid(), userEntity.getId());
        assertEquals(user.getName(), userEntity.getName());
        assertEquals(user.getEmail().value(), userEntity.getEmail());
        assertEquals(user.getPassword().getHashedValue(), userEntity.getHashedPassword());
        assertEquals(user.getAddress().getCity(), userEntity.getAddress().getCity());
        assertEquals(user.getAddress().getCountry(), userEntity.getAddress().getCountry());
        assertEquals(user.getAddress().getZipCode(), userEntity.getAddress().getZipCode());
        assertEquals(user.getAddress().getStreet(), userEntity.getAddress().getStreet());
        assertEquals(user.getFavoriteProductIds().stream().map(FavoriteId::value).collect(Collectors.toSet()), userEntity.getFavoriteProductIds());
        assertEquals(user.getLoyaltyPoints().getValue(), userEntity.getLoyaltyPoints());
    }
}
