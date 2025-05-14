package com.gft.user.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
@Getter
@AllArgsConstructor
@Generated
public class Address {

    private final String country;
    private final String zipCode;
    private final String city;
    private final String street;

}
