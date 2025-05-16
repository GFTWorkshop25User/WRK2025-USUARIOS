package com.gft.user.domain.model.user;

import org.mindrot.jbcrypt.BCrypt;
import lombok.Getter;

@Getter
public class Password {

    private final String hashedValue;

    private Password(String hashedValue) {
        this.hashedValue = hashedValue;
    }

    public static Password createPasswordFromPlain(String plainPassword) {
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        return new Password(BCrypt.hashpw(plainPassword, BCrypt.gensalt()));
    }

    public static Password createPasswordFromHashed(String hashedValue) {
        if (hashedValue == null || hashedValue.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return new Password(hashedValue);
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, hashedValue);
    }

}
