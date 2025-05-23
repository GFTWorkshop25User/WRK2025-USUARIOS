package com.gft.user.application.usecase.usermanagement;

import com.gft.user.application.usecase.BaseUseCase;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangeUserNameUseCase extends BaseUseCase {

    private final Logger logger = LoggerFactory.getLogger(ChangeUserNameUseCase.class);

    public ChangeUserNameUseCase(UserRepository userRepository) {
        super(userRepository);
    }

    @Transactional
    public void execute(UUID userId, String newUserName) {

        throwIfUserDoesntExistOrIsDisabled(userId);

        User user = this.userRepository.getById(userId);
        String oldName = user.getName();
        user.changeName(newUserName);

        logger.info("Changed user name [{}] to [{}] to user [{}]", oldName, newUserName, userId);
        this.userRepository.save(user);
    }
}
