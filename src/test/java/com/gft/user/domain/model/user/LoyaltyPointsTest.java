package com.gft.user.domain.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoyaltyPointsTest {

    @Test
    public void should_throwIllegalArgumentException_when_valueIsNegative() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new LoyaltyPoints(-10));
        assertEquals("Loyalty points cannot be negative", exception.getMessage());
    }

    @Test
    public void should_createLoyaltyPoints_when_valueIsPositive() {
        var loyaltyPoints = new LoyaltyPoints(10);
        assertEquals(10, loyaltyPoints.getValue());
    }

    @Test
    public void should_createLoyaltyPoints_when_valueIsZero() {
        var loyaltyPoints = new LoyaltyPoints(0);
        assertEquals(0, loyaltyPoints.getValue());
    }

    @Test
    public void should_throwIllegalArgumentException_when_incrementNegativeQuantity() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        var exception = assertThrows(IllegalArgumentException.class, () -> loyaltyPoints.increment(-1));
        assertEquals("Increment quantity cannot be negative", exception.getMessage());
        assertEquals(10, loyaltyPoints.getValue());
    }

    @Test
    public void should_incrementQuantity_when_incrementPositiveQuantity() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        loyaltyPoints.increment(5);
        assertEquals(15, loyaltyPoints.getValue());
    }

    @Test
    public void should_throwIllegalArgumentException_when_decrementNegativeQuantity() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        var exception = assertThrows(IllegalArgumentException.class, () -> loyaltyPoints.decrement(-1));
        assertEquals("Decrement quantity cannot be negative", exception.getMessage());
        assertEquals(10, loyaltyPoints.getValue());
    }

    @Test
    public void should_throwIllegalArgumentException_when_remainingQuantityIsNegative() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        var exception = assertThrows(IllegalArgumentException.class, () -> loyaltyPoints.decrement(15));
        assertEquals("Remaining points cannot be negative", exception.getMessage());
        assertEquals(10, loyaltyPoints.getValue());
    }

    @Test
    public void should_decrementQuantity_when_decrementPositiveQuantity() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        loyaltyPoints.decrement(5);
        assertEquals(5, loyaltyPoints.getValue());
    }

}
