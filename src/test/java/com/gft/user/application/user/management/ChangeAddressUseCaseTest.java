package com.gft.user.application.user.management;

import com.gft.user.domain.model.user.*;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChangeAddressUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChangeAddressUseCase changeAddressUseCase;

    private final UUID userId = UUID.randomUUID();

    private final User user = User.create(
            UserId.create(userId),
            "Alfonso Gutierrez",
            new Email("alfonsito@gmail.com"),
            Password.createPasswordFromPlain("Pepito1234!!"),
            new Address("", "", "", ""),
            new HashSet<>(),
            new LoyaltyPoints(0),
            false
    );

    private final Address address = new Address(
            "Germany",
            "85215",
            "Berlin",
            "Der Gesang der toten Kolibris"
    );

    @Test
    void should_throwUserNotFoundException_when_userDoesNotExist() {
        when(userRepository.existsById(userId)).thenReturn(false);

        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> changeAddressUseCase.execute(userId, address));

        assertEquals(String.format("User with id %s not found", userId), userNotFoundException.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_addressCountryIsBlank() {
        Address invalidAddress = new Address(
                "",
                "85215",
                "Berlin",
                "Der Gesang der toten Kolibris"
        );

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> changeAddressUseCase.execute(userId, invalidAddress));
        assertEquals("Address cannot have empty fields", illegalArgumentException.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_addressZipCodeIsBlank() {
        Address invalidAddress = new Address(
                "Germany",
                " ",
                "Berlin",
                "Der Gesang der toten Kolibris"
        );

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> changeAddressUseCase.execute(userId, invalidAddress));
        assertEquals("Address cannot have empty fields", illegalArgumentException.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_addressCityIsBlank() {
        Address invalidAddress = new Address(
                "Germany",
                "85215",
                " ",
                "Der Gesang der toten Kolibris"
        );

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> changeAddressUseCase.execute(userId, invalidAddress));
        assertEquals("Address cannot have empty fields", illegalArgumentException.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_addressStreetIsBlank() {
        Address invalidAddress = new Address(
                "Germany",
                "85215",
                "Berlin",
                ""
        );

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> changeAddressUseCase.execute(userId, invalidAddress));
        assertEquals("Address cannot have empty fields", illegalArgumentException.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_addressIsNull() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> changeAddressUseCase.execute(userId, null));

        assertEquals("Address cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void should_changeAddress_when_addressIsValid() {
        when(userRepository.getById(userId)).thenReturn(user);
        when(userRepository.existsById(userId)).thenReturn(true);

        changeAddressUseCase.execute(userId, address);

        assertEquals(address, user.getAddress());
        assertEquals(address.city(), user.getAddress().city());
        assertEquals(address.zipCode(), user.getAddress().zipCode());
        assertEquals(address.country(), user.getAddress().country());
        assertEquals(address.street(), user.getAddress().street());
    }
}
