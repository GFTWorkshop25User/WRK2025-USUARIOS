package com.gft.user.domain.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserIdTest {

    @Test
    void should_notBeNull_when_constructorCalled() {
        UserId id = new UserId();
        assertNotEquals(null, id);
    }

}
