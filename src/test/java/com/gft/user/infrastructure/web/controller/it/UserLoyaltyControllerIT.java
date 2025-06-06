package com.gft.user.infrastructure.web.controller.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.user.application.usecase.loyalty.DecrementLoyaltyPointsUseCase;
import com.gft.user.application.usecase.loyalty.GetUserLoyaltyPointsUseCase;
import com.gft.user.application.usecase.loyalty.IncrementLoyaltyPointsUseCase;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import com.gft.user.infrastructure.web.controller.UserLoyaltyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
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

@ActiveProfiles("test")
@WebMvcTest(UserLoyaltyController.class)
class UserLoyaltyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GetUserLoyaltyPointsUseCase getUserLoyaltyPointsCaseUseCase;

    @MockitoBean
    private IncrementLoyaltyPointsUseCase incrementLoyaltyPointsUseCase;

    @MockitoBean
    private DecrementLoyaltyPointsUseCase decrementLoyaltyPointsUseCase;

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
