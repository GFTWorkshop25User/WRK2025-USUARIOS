package com.gft.user.domain.model.user;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

@ValueObject
public record Email(String value) {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public Email {
        Assert.notNull(value, "Email cannot be null");
        if (!isValid(value)) {
            throw new IllegalArgumentException("Email is not valid");
        }
    }

    private boolean isValid(String email) {
        return email.matches(EMAIL_REGEX);
    }

}