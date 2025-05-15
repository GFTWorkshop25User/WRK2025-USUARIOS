package com.gft.user.domain.model.user;

import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

@AggregateRoot
@Getter
public class User {

    private final UserId id;
    private final String name;
    private final Email email;
    private final Password password;
    private final Address address;
    private final Set<FavoriteId> favoriteProductIds;
    private final LoyaltyPoints loyaltyPoints;
    private boolean disabled;

    private User(UserId id, String name, Email email, Password password, Address address, Set<FavoriteId> favoriteProductIds, LoyaltyPoints loyaltyPoints, boolean disabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.favoriteProductIds = favoriteProductIds;
        this.loyaltyPoints = loyaltyPoints;
        this.disabled = disabled;
    }

    public void disableUser() {
        this.disabled = true;
    }

    public static User create(UserId id, String name, Email email, Password password, Address address, Set<FavoriteId> favoriteProductIds, LoyaltyPoints loyaltyPoints, boolean disabled) {
        return new User(id, name, email, password, address, favoriteProductIds, loyaltyPoints, disabled);
    }

    public static User register(String name, Email email, Password password) {
        Assert.notNull(name, "Name cannot be null");
        Assert.notNull(email, "Email cannot be null");
        Assert.notNull(password, "Password cannot be null");

        return new User(
                new UserId(),
                name,
                email,
                password,
                null,
                new HashSet<>(),
                new LoyaltyPoints(0),
                false
        );
    }
}
