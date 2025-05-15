package com.gft.user.infrastructure.mapper;

import com.gft.user.domain.model.user.Address;
import com.gft.user.infrastructure.entity.AddressEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    void should_returnNull_when_mapToAddressEntityIsNull() {
        AddressEntity addressEntity = addressMapper.toAddressEntity(null);
        assertNull(addressEntity);
    }

    @Test
    void should_mapAddress_to_addressEntity() {
        Address address = new Address("Spain", "241852", "Villalba", "Calle los floriponcios");
        AddressEntity addressEntity = addressMapper.toAddressEntity(address);

        assertEquals(address.city(), addressEntity.getCity());
        assertEquals(address.country(), addressEntity.getCountry());
        assertEquals(address.zipCode(), addressEntity.getZipCode());
        assertEquals(address.street(), addressEntity.getStreet());
    }

    @Test
    void should_returnNull_when_mapFromAddressEntityIsNull() {
        Address address = addressMapper.fromAddressEntity(null);
        assertNull(address);
    }

    @Test
    void should_mapAddressEntity_to_address() {
        AddressEntity addressEntity = new AddressEntity("Spain", "241852", "Villalba", "Calle los floriponcios");
        Address address = addressMapper.fromAddressEntity(addressEntity);

        assertEquals(addressEntity.getCity(), address.city());
        assertEquals(addressEntity.getCountry(), address.country());
        assertEquals(addressEntity.getZipCode(), address.zipCode());
        assertEquals(addressEntity.getStreet(), address.street());
    }

}
