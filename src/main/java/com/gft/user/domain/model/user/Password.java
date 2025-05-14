package com.gft.user.domain.model.user;

import org.mindrot.jbcrypt.BCrypt;
import lombok.Getter;

@Getter
public class Password {

    private final String hashedValue;

    public Password(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.hashedValue = BCrypt.hashpw(value, BCrypt.gensalt());
        System.out.println(hashedValue);
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, hashedValue);
    }
}
