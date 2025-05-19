package com.gft.user.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.user.application.user.loyalty.GetUserLoyaltyPointsUseCase;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserLoyaltyController.class)
public class UserLoyaltyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GetUserLoyaltyPointsUseCase getUserLoyaltyPointsCaseUseCase;

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
}
