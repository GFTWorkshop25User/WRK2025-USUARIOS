package com.gft.user.application.usecase.usermanagement;

import com.gft.user.application.dto.UserRequest;
import com.gft.user.domain.factory.PasswordFactory;
import com.gft.user.domain.model.user.Email;
import com.gft.user.domain.model.user.Password;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.EmailAlreadyRegisteredException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRegistrationUseCase {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserRegistrationUseCase.class);

    public UserRegistrationUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UUID execute(UserRequest userRequest) {

        throwIfEmailAlreadyRegistered(userRequest.email());

        PasswordFactory passwordFactory = new PasswordFactory();
        Password password = passwordFactory.createFromPlainText(userRequest.plainPassword());

        User user = User.register(userRequest.name(), new Email(userRequest.email()), password);

        logger.info("Registered user: [{}], [{}], [{}]", user.getId(), user.getName(), user.getEmail());
        return userRepository.save(user);
    }

    private void throwIfEmailAlreadyRegistered(String email) {
        if (userRepository.existsByEmail(email)){
            logger.warn("Tried to register a user with an email address already in use.");
            throw new EmailAlreadyRegisteredException(String.format("Email %s is already being used", email));
        }
    }
}
