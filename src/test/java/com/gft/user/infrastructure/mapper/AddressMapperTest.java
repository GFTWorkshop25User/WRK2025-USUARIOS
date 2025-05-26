package com.gft.user.infrastructure.mapper;

import com.gft.user.domain.model.user.Address;
import com.gft.user.infrastructure.entity.AddressEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class AddressMapperTest {

    private final AddressMapper addressMapper = new AddressMapper();

    @Test
    void should_returnNull_when_mapToAddressEntityIsNull() {
        AddressEntity addressEntity = addressMapper.toAddressEntity(null);
        assertNull(addressEntity);
    }

    @Test
    void should_mapAddress_to_addressEntity() {
        Address address = new Address("Spain", "241852", "Villalba", "Calle los floriponcios");
        AddressEntity addressEntity = addressMapper.toAddressEntity(address);

        assertThat(address).usingRecursiveComparison().isEqualTo(addressEntity);
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

        assertThat(addressEntity).usingRecursiveComparison().isEqualTo(address);
    }

}
