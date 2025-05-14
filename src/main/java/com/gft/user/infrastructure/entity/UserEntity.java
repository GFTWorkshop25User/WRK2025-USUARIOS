package com.gft.user.infrastructure.entity;

import jakarta.persistence.*;
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
@Table(name = "USERS")
public class UserEntity {

    @Id
    private UUID id;

    private String name;
    private String email;
    private String hashedPassword;

    @Embedded
    private AddressEntity address;

    @ElementCollection
    @CollectionTable(
            name = "USER_FAVORITE_PRODUCTS",
            joinColumns = @JoinColumn(name = "USER_ID")
    )
    @Column(name = "PRODUCT_ID")
    private Set<Long> favoriteProductIds;

    private int loyaltyPoints;

}
