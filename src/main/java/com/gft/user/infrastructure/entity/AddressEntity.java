package com.gft.user.infrastructure.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class AddressEntity {

    private String country;
    private String zipCode;
    private String city;
    private String street;

}
