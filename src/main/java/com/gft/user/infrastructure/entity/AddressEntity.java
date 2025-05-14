package com.gft.user.infrastructure.entity;

import jakarta.persistence.Embeddable;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Generated
public class AddressEntity {

    private String country;
    private String zipCode;
    private String city;
    private String street;

}
