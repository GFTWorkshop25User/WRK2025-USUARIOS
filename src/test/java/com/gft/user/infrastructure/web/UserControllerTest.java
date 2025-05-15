package com.gft.user.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.user.application.user.GetUserByIdUseCase;
import com.gft.user.application.user.UserRegistrationUseCase;
import com.gft.user.application.user.dto.UserRequest;
import com.gft.user.domain.model.user.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                new Password("123456"),
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

}
