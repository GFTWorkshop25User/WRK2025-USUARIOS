package com.gft.user.domain.model.user;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record Email(String value) {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public Email {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
    }

    private boolean isValid(String email) {
        return email.matches(EMAIL_REGEX);
    }

}