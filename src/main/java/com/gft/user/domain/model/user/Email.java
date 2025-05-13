package com.gft.user.domain.model.user;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record Email(String value) {

    public Email {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
    }

    private boolean isValid(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

}
