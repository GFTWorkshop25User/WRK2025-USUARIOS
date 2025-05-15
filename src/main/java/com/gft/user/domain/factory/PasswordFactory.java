package com.gft.user.domain.factory;

import com.gft.user.domain.model.user.Password;
import com.gft.user.domain.service.PasswordStrengthValidator;
import org.springframework.util.Assert;

public class PasswordFactory {

    public Password createFromPlainText(String plainTextPassword) {
        Assert.notNull(plainTextPassword, "Password cannot be null");

        PasswordStrengthValidator passwordStrengthValidator = new PasswordStrengthValidator();

        if (!passwordStrengthValidator.validate(plainTextPassword)) {
            throw new IllegalArgumentException("Password must be at least 12 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character");
        }

        return new Password(plainTextPassword);
    }
}
