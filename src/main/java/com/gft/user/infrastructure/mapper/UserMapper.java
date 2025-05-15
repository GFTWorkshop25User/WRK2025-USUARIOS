package com.gft.user.infrastructure.mapper;

import com.gft.user.domain.model.user.FavoriteId;
import com.gft.user.domain.model.user.User;
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

        return new UserEntity(user.getId().getUuid(), user.getName(), user.getEmail().value(), user.getPassword().getHashedValue(), addressMapper.toAddressEntity(user.getAddress()), user.getFavoriteProductIds().stream().map(FavoriteId::value).collect(Collectors.toSet()), user.getLoyaltyPoints().getValue());
    }
}
