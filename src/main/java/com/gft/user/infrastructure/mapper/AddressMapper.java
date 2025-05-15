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

        return new AddressEntity(address.getCountry(), address.getZipCode(), address.getCity(), address.getStreet());
    }
}
