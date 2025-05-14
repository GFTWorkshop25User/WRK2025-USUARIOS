package com.gft.user.domain.model.user;

import lombok.Getter;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
@Getter
public class LoyaltyPoints {

    private int value;

    public LoyaltyPoints(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Loyalty points cannot be negative");
        }
        this.value = value;
    }

    public void increment(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Increment quantity cannot be negative");
        }
        this.value += quantity;
    }

    public void decrement(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Decrement quantity cannot be negative");
        }
        if (this.value - quantity < 0) {
            throw new IllegalArgumentException("Remaining points cannot be negative");
        }
        this.value -= quantity;
    }
}
