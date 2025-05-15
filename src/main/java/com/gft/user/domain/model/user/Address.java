package com.gft.user.domain.model.user;

import lombok.Generated;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
@Generated
public record Address(String country, String zipCode, String city, String street) {

}
