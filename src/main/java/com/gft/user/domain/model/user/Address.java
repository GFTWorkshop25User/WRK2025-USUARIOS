package com.gft.user.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
@Generated
public record Address(String country, String zipCode, String city, String street) {

}
