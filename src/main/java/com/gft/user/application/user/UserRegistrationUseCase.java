package com.gft.user.application.user;

import com.gft.user.application.user.dto.UserRequest;
import com.gft.user.domain.factory.PasswordFactory;
import com.gft.user.domain.model.user.Email;
import com.gft.user.domain.model.user.Password;
import com.gft.user.domain.model.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationUseCase {

    public User execute(UserRequest userRequest) {
        PasswordFactory passwordFactory = new PasswordFactory();
        Password password = passwordFactory.createFromPlainText(userRequest.plainPassword());

        return User.register(userRequest.name(), new Email(userRequest.email()), password);
    }
}
