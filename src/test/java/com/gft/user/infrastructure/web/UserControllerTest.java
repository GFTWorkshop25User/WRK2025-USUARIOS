package com.gft.user.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.user.application.user.ChangePasswordUseCase;
import com.gft.user.application.user.GetUserByIdUseCase;
import com.gft.user.application.user.DeleteUserUseCase;
import com.gft.user.application.user.UserRegistrationUseCase;
import com.gft.user.application.user.dto.ChangePasswordRequest;
import com.gft.user.application.user.dto.UserRequest;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

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
    private ChangePasswordUseCase changePasswordUseCase;

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
        MvcResult mvcResult= mockMvc.perform(get("/api/v1/users/" + uuid).content(requestBody).contentType(MediaType.APPLICATION_JSON))
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

}
