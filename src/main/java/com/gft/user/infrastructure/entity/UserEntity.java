package com.gft.user.infrastructure.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Generated
public class UserEntity {

    @Id
    private UUID id;

    private String name;
    private String email;
    private String hashedPassword;

    @Embedded
    private AddressEntity address;

    private Set<Long> favoriteProductIds;
    private int loyaltyPoints;

}
