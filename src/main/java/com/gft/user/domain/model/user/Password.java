package com.gft.user.domain.model.user;

import lombok.Getter;

@Getter
public class Password {

    private final String hashedValue;

    Password(String value) {
        // TODO: implementar hash
        this.hashedValue = value;
    }
}
