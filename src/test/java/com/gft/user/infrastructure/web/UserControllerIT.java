package com.gft.user.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.user.application.user.*;
import com.gft.user.application.user.dto.ChangePasswordRequest;
import com.gft.user.application.user.dto.UserRequest;
import com.gft.user.domain.exception.ProductNotInFavoritesException;
import com.gft.user.domain.model.user.*;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserRegistrationUseCase userRegistrationUseCase;

    @MockitoBean
    private GetUserByIdUseCase getUserByIdUseCase;

    @MockitoBean
    private DeleteUserUseCase deleteUserUseCase;

    @MockitoBean
    private ChangeUserNameUseCase changeUserNameUseCase;

    @MockitoBean
    private ChangeEmailUseCase changeEmailUseCase;

    @MockitoBean
    private ChangePasswordUseCase changePasswordUseCase;

    @MockitoBean
    private ChangeAddressUseCase changeAddressUseCase;

    @MockitoBean
    private GetUserLoyaltyPointsUseCase getUserLoyaltyPointsCaseUseCase;

    @MockitoBean
    private GetFavoriteProductsUseCase getFavoriteProductsUseCase;

    @MockitoBean
    private RemoveUserFavoriteProductUseCase removeUserFavoriteProductUseCase;

    @Test
    void should_responseCreated_when_userRequestIsValid() throws Exception {
        UUID uuid = UUID.randomUUID();
        UserRequest userRequest = new UserRequest("Pepe", "pepe@mail.com", "Pepito123456!!");
        when(userRegistrationUseCase.execute(userRequest)).thenReturn(uuid);

        String requestBody = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/v1/users").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/users/" + uuid));
    }

    @Test
    void should_responseFound_when_userRequestIsValid() throws Exception {
        UUID uuid = UUID.randomUUID();
        User user = User.create(
                new UserId(),
                "miguel",
                new Email("miguel@gmail.com"),
                Password.createPasswordFromHashed("$2a$10$hZwpOSjHC/eNQAqFYDHG4OuVDQ1U.JX6QKg/fBi9uML.Xp/p8h8qe"),
                new Address("Spain", "241852", "Villalba", "Calle los floriponcios"),
                new HashSet<>(),
                new LoyaltyPoints(0),
                false
        );
        when(getUserByIdUseCase.execute(uuid)).thenReturn(user);

        String requestBody = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/" + uuid).content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBodyExpected = objectMapper.writeValueAsString(user);
        String responseBodyActual = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(responseBodyActual).isEqualToIgnoringWhitespace(responseBodyExpected);
    }


    @Test
    void should_responseNotFound_when_deleteUserNotFound() throws Exception {
        UUID uuid = UUID.randomUUID();

        doThrow(new UserNotFoundException("User with id " + uuid + " not found")).when(deleteUserUseCase).execute(uuid);

        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/users/{id}", uuid)).andExpect(status().isNotFound()).andReturn();

        assertEquals("User with id " + uuid + " not found", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }


    @Test
    void should_noResponseAndDisableUser_when_deleteUser() throws Exception {
        UUID uuid = UUID.randomUUID();

        doNothing().when(deleteUserUseCase).execute(uuid);

        mockMvc.perform(delete("/api/v1/users/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(deleteUserUseCase, times(1)).execute(uuid);
    }

    @Test
    void should_noResponseAndChangePassword_when_changePasswordIsValid() throws Exception {
        UUID uuid = UUID.randomUUID();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("Pepito123456!", "Josep123456!");

        doNothing().when(changePasswordUseCase).execute(uuid, changePasswordRequest.oldPassword(), changePasswordRequest.newPassword());

        mockMvc.perform(put("/api/v1/users/{id}/change-password", uuid)
                        .content(objectMapper.writeValueAsString(changePasswordRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(changePasswordUseCase, times(1)).execute(uuid, changePasswordRequest.oldPassword(), changePasswordRequest.newPassword());

    }

    @Test
    void should_responseNotFound_when_changeEmailUserIdIsInvalid() throws Exception {
        UUID uuid = UUID.randomUUID();
        String newEmail = "juanmiguel@gmail.com";

        doThrow(new UserNotFoundException("User with id " + uuid + " not found")).when(changeEmailUseCase).execute(uuid, newEmail);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/users/{userId}/change-email", uuid)
                        .content(newEmail))
                .andExpect(status().isNotFound())
                .andReturn();

        assertEquals("User with id " + uuid + " not found", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_responseNotFound_when_userToChangeNameNotFound() throws Exception {
        UUID uuid = UUID.randomUUID();

        doThrow(new UserNotFoundException("User with id " + uuid + " not found")).when(changeUserNameUseCase).execute(uuid, "New name");

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/users/{id}/change-name", uuid).content("New name").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isNotFound()).andReturn();

        assertEquals("User with id " + uuid + " not found", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_responseBadRequest_when_changeEmailEmailIsInvalid() throws Exception {
        UUID uuid = UUID.randomUUID();
        String newEmail = "juanmiguel@gmail";

        doThrow(new IllegalArgumentException("Email is not valid")).when(changeEmailUseCase).execute(uuid, newEmail);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/users/{userId}/change-email", uuid)
                        .content(newEmail)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Email is not valid", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_responseBadRequest_when_changeEmailEmailIsEmpty() throws Exception {
        UUID uuid = UUID.randomUUID();
        String newEmail = " ";

        doThrow(new IllegalArgumentException("Email cannot be empty")).when(changeEmailUseCase).execute(uuid, newEmail);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/users/{id}/change-email", uuid)
                        .content(newEmail)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Email cannot be empty", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_responseBadRequest_when_changeAddressAddressHasNullFields() throws Exception {
        UUID uuid = UUID.randomUUID();
        Address address = new Address(" ", "85215", "Berlin", "Der Gesang der toten Kolibris");

        doThrow(new IllegalArgumentException("Address cannot have an empty field")).when(changeAddressUseCase).execute(uuid, address);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/users/{id}/change-address", uuid)
                        .content(objectMapper.writeValueAsString(address))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Address cannot have an empty field", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_noResponseAndChangeEmail_when_changeEmail() throws Exception {
        UUID userId = UUID.randomUUID();
        String newEmail = "juanmiguel@gmail.com";

        doNothing().when(changeEmailUseCase).execute(userId, newEmail);

        mockMvc.perform(put("/api/v1/users/{userId}/change-email", userId)
                        .content(newEmail)
                        .contentType(MediaType.TEXT_PLAIN)).
                andExpect(status().isNoContent());

        verify(changeEmailUseCase, times(1)).execute(userId, newEmail);
    }

    @Test
    void should_noResponseAndChangeUserName_when_changeNamePutCalled() throws Exception {
        UUID uuid = UUID.randomUUID();

        doNothing().when(changeUserNameUseCase).execute(uuid, "New name");

        mockMvc.perform(put("/api/v1/users/{id}/change-name", uuid)
                        .content("New name")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isNoContent());

        verify(changeUserNameUseCase, times(1)).execute(uuid, "New name");
    }

    @Test
    void should_noResponseAndChangeAddress_when_changeAddressPutCalled() throws Exception {
        UUID uuid = UUID.randomUUID();

        Address address = new Address("Germany", "85215", "Berlin", "Der Gesang der toten Kolibris");
        doNothing().when(changeAddressUseCase).execute(uuid, address);

        mockMvc.perform(put("/api/v1/users/{id}/change-address", uuid)
                        .content(objectMapper.writeValueAsString(address))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(changeAddressUseCase, times(1)).execute(uuid, address);
    }

    @Test
    void should_responseNotFound_when_userNotFoundForLoyaltyPoints() throws Exception {
        UUID uuid = UUID.randomUUID();

        doThrow(new UserNotFoundException("User with id " + uuid + " not found")).when(getUserLoyaltyPointsCaseUseCase).execute(uuid);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/{id}/loyalty-points", uuid)).andExpect(status().isNotFound()).andReturn();

        assertEquals("User with id " + uuid + " not found", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_returnOk_when_userFoundForLoyaltyPoints() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(getUserLoyaltyPointsCaseUseCase.execute(uuid)).thenReturn(4);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/{id}/loyalty-points", uuid)).andExpect(status().isOk()).andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(4);
        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void should_responseNotFound_when_userNotFoundForGetFavoriteIds() throws Exception {
        UUID uuid = UUID.randomUUID();

        doThrow(new UserNotFoundException("User with id " + uuid + " not found")).when(getFavoriteProductsUseCase).execute(uuid);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/{id}/favorite-products", uuid)).andExpect(status().isNotFound()).andReturn();

        assertEquals("User with id " + uuid + " not found", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_returnOk_when_userFoundForGetFavoriteIds() throws Exception {
        UUID uuid = UUID.randomUUID();

        Set<FavoriteId> favoriteIds = new HashSet<>();
        favoriteIds.add(new FavoriteId(4L));
        when(getFavoriteProductsUseCase.execute(uuid)).thenReturn(favoriteIds);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/{id}/favorite-products", uuid)).andExpect(status().isOk()).andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(favoriteIds);
        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void should_noResponse_when_removeLoyaltyPoints() throws Exception {
        UUID uuid = UUID.randomUUID();
        long productId = 4L;

        doNothing().when(removeUserFavoriteProductUseCase).execute(uuid, productId);

        mockMvc.perform(put("/api/v1/users/{id}/favorite-products/remove", uuid)
                        .content(objectMapper.writeValueAsString(productId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(removeUserFavoriteProductUseCase, times(1)).execute(uuid, productId);
    }

    @Test
    void should_responseNotFound_when_userNotFoundForRemoveLoyaltyPoints() throws Exception {
        UUID uuid = UUID.randomUUID();
        long productId = 4L;

        doThrow(new UserNotFoundException("User with id " + uuid + " not found")).when(removeUserFavoriteProductUseCase).execute(uuid, productId);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/users/{id}/favorite-products/remove", uuid)
                        .content(objectMapper.writeValueAsString(productId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        assertEquals("User with id " + uuid + " not found", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_responseBadRequest_when_favoriteToRemoveNotFound() throws Exception {
        UUID uuid = UUID.randomUUID();
        long productId = 8L;

        doThrow(new ProductNotInFavoritesException("Product 8 is not in favorite products")).when(removeUserFavoriteProductUseCase).execute(uuid, productId);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/users/{id}/favorite-products/remove", uuid)
                        .content(objectMapper.writeValueAsString(productId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Product 8 is not in favorite products", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
}
