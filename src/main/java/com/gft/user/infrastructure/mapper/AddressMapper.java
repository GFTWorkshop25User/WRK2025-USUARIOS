package com.gft.user.infrastructure.mapper;

import com.gft.user.domain.model.user.Address;
import com.gft.user.infrastructure.entity.AddressEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {

    public AddressEntity toAddressEntity(Address address) {
        if(address == null) {
            return null;
        }

        return new AddressEntity(
                address.country(),
                address.zipCode(),
                address.city(),
                address.street()
        );
    }

    public Address fromAddressEntity(AddressEntity addressEntity) {
        if(addressEntity == null) {
            return null;
        }

        return new Address(
                addressEntity.getCountry(),
                addressEntity.getZipCode(),
                addressEntity.getCity(),
                addressEntity.getStreet()
        );
    }

}
