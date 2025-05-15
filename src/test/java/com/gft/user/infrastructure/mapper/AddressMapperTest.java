package com.gft.user.infrastructure.mapper;

import com.gft.user.domain.model.user.Address;
import com.gft.user.infrastructure.entity.AddressEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    void should_map_address_to_addressEntity() {
        Address address = new Address("Spain", "241852", "Villalba", "Calle los floriponcios");
        AddressEntity addressEntity = addressMapper.toAddressEntity(address);

        assertEquals(address.getCity(), addressEntity.getCity());
        assertEquals(address.getCountry(), addressEntity.getCountry());
        assertEquals(address.getZipCode(), addressEntity.getZipCode());
        assertEquals(address.getStreet(), addressEntity.getStreet());
    }

}
