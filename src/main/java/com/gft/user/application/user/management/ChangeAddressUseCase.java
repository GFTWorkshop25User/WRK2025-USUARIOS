package com.gft.user.application.user.management;

import com.gft.user.domain.model.user.Address;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
public class ChangeAddressUseCase {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(ChangeAddressUseCase.class);

    public ChangeAddressUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID userId, Address address) {
        Assert.notNull(address, "Address cannot be null");
        Assert.notNull(address.city(), "City cannot be null");
        Assert.notNull(address.country(), "Country cannot be null");
        Assert.notNull(address.street(), "Street cannot be null");
        Assert.notNull(address.zipCode(), "ZipCode cannot be null");

        if (address.city().isBlank() || address.street().isBlank() || address.zipCode().isBlank() || address.country().isBlank()) {
            logger.warn("Tried to change address with an empty field");
            throw new IllegalArgumentException("Address cannot have empty fields");
        }

        if(!userRepository.existsByIdAndDisabledFalse(userId)) {
            logger.warn("Tried to change address to a disabled or non-existing user [{}]", userId);
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = userRepository.getById(userId);
        user.changeAddress(address);

        logger.info("Changed address to user [{}]", userId);
        userRepository.save(user);
    }
}
