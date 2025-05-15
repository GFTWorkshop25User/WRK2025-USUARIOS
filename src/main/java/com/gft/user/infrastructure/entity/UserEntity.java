package com.gft.user.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "USER_FAVORITE_PRODUCTS",
            joinColumns = @JoinColumn(name = "USER_ID")
    )
    @Column(name = "PRODUCT_ID")
    private Set<Long> favoriteProductIds;

    private int loyaltyPoints;
    private boolean disabled;
}
