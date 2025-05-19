package com.gft.user.application.user;

import com.gft.user.domain.model.user.Address;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
public class ChangeAddressUseCase {
    private final UserRepository userRepository;

    public ChangeAddressUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID userId, Address address) {
        Assert.notNull(address, "Address cannot be null");

        if (address.city().isBlank() || address.street().isBlank() || address.zipCode().isBlank() || address.country().isBlank()) {
            throw new IllegalArgumentException("Address cannot have empty fields");
        }

        if(!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("User with id %s not found", userId));
        }

        User user = userRepository.getById(userId);
        user.changeAddress(address);

        userRepository.save(user);
    }
}
