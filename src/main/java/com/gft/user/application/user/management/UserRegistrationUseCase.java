package com.gft.user.application.user.management;

import com.gft.user.application.user.management.dto.UserRequest;
import com.gft.user.domain.factory.PasswordFactory;
import com.gft.user.domain.model.user.Email;
import com.gft.user.domain.model.user.Password;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRegistrationUseCase {

    private final UserRepository userRepository;

    public UserRegistrationUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UUID execute(UserRequest userRequest) {
        PasswordFactory passwordFactory = new PasswordFactory();
        Password password = passwordFactory.createFromPlainText(userRequest.plainPassword());

        User user = User.register(userRequest.name(), new Email(userRequest.email()), password);

        return userRepository.save(user);
    }
}
