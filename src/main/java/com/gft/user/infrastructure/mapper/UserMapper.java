package com.gft.user.infrastructure.mapper;

import com.gft.user.domain.model.user.*;
import com.gft.user.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserMapper {

    private final AddressMapper addressMapper;

    public UserMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public UserEntity toUserEntity(User user) {
        return new UserEntity(
                user.getId().getUuid(),
                user.getName(),
                user.getEmail().value(),
                user.getPassword().getHashedValue(),
                addressMapper.toAddressEntity(user.getAddress()),
                user.getFavoriteProductIds().stream().map(FavoriteId::value).collect(Collectors.toSet()),
                user.getLoyaltyPoints().getValue(),
                user.isDisabled()
        );
    }

    public User fromUserEntity(UserEntity userEntity) {
        return User.create(
                UserId.create(userEntity.getId()),
                userEntity.getName(),
                new Email(userEntity.getEmail()),
                new Password(new SafeString(userEntity.getHashedPassword())),
                addressMapper.fromAddressEntity(userEntity.getAddress()),
                userEntity.getFavoriteProductIds().stream().map(FavoriteId::new).collect(Collectors.toSet()),
                new LoyaltyPoints(userEntity.getLoyaltyPoints()),
                userEntity.isDisabled()
        );
    }

}
