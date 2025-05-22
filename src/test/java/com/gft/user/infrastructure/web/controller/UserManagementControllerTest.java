package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.user.management.dto.ChangePasswordRequest;
import com.gft.user.application.user.management.dto.UserRequest;
import com.gft.user.application.user.management.*;
import com.gft.user.domain.model.user.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementControllerTest {

    @InjectMocks
    private UserManagementController userManagementController;

    @Mock
    private UserRegistrationUseCase userRegistrationUseCase;

    @Mock
    private GetUserByIdUseCase getUserByIdUseCase;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @Mock
    private ChangeUserNameUseCase changeUserNameUseCase;

    @Mock
    private ChangeEmailUseCase changeEmailUseCase;

    @Mock
    private ChangePasswordUseCase changePasswordUseCase;

    @Mock
    private ChangeAddressUseCase changeAddressUseCase;

    @Test
    void should_returnCreatedLocation_when_userRegisteredSuccessfully() {
        UUID uuid = UUID.randomUUID();
        UserRequest userRequest = new UserRequest("Pepe", "pepe@mail.com", "Pepito123456!!");
        when(userRegistrationUseCase.execute(userRequest)).thenReturn(uuid);


        ResponseEntity<UUID> response = userManagementController.registerUser(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(uuid, response.getBody());

        verify(userRegistrationUseCase, times(1)).execute(userRequest);
    }

    @Test
    void should_returnUser_when_userExists() {
        UUID uuid = UUID.randomUUID();
        User user = User.create(
                UserId.create(uuid),
                "Alfonso Gutierrez",
                new Email("alfonsito@gmail.com"),
                Password.createPasswordFromHashed("$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe!!"),
                new Address("", "", "", ""),
                new HashSet<>(),
                new LoyaltyPoints(0),
                false
        );

        when(getUserByIdUseCase.execute(uuid)).thenReturn(user);

        User response = userManagementController.getUser(uuid);

        assertEquals(user, response);

        verify(getUserByIdUseCase, times(1)).execute(uuid);
    }

    @Test
    void should_callDeleteUserUseCase_when_deleteUserCalled() {
        UUID uuid = UUID.randomUUID();

        userManagementController.deleteUser(uuid);

        verify(deleteUserUseCase).execute(uuid);
    }

    @Test
    void should_changeUserName_when_changeUserNameCalled() {
        UUID uuid = UUID.randomUUID();
        String newName = "New Name";

        userManagementController.updateUserName(uuid, newName);

        verify(changeUserNameUseCase, times(1)).execute(uuid, newName);
    }

    @Test
    void should_changeEmail_when_changeEmailCalled() {
        UUID uuid = UUID.randomUUID();
        String newEmail = "new@email.com";

        userManagementController.changeEmail(uuid, newEmail);

        verify(changeEmailUseCase, times(1)).execute(uuid, newEmail);
    }

    @Test
    void should_changePassword_when_changePasswordCalled() {
        UUID uuid = UUID.randomUUID();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("Password123456!", "newPassword123456!");

        userManagementController.changePassword(uuid, changePasswordRequest);

        verify(changePasswordUseCase).execute(uuid, "Password123456!", "newPassword123456!");
    }
  
    @Test
    void should_changeAddress_when_changeAddressCalled() {
        UUID uuid = UUID.randomUUID();
        Address address = new Address("Germany", "85215", "Berlin", "Der Gesang der toten Kolibris");

        userManagementController.changeAddress(uuid, address);

        verify(changeAddressUseCase).execute(uuid, address);
    }
}
