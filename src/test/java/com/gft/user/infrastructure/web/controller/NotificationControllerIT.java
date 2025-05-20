package com.gft.user.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.user.application.notification.dto.NotificationDto;
import com.gft.user.application.notification.usecase.GetUserNotificationsUseCase;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebMvcTest(NotificationController.class)
class NotificationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GetUserNotificationsUseCase getUserNotificationsUseCase;

    @Test
    void should_returnUserNotifications_when_getUserNotificationsCalled() throws Exception {
        UUID uuid = UUID.randomUUID();
        List<NotificationDto> notificationsListSent = new ArrayList<>();

        NotificationDto notificationDto = new NotificationDto("Nombre actualizado", LocalDateTime.now(), true);
        notificationsListSent.add(notificationDto);

        String requestBody = objectMapper.writeValueAsString(notificationsListSent);
        when(getUserNotificationsUseCase.execute(uuid)).thenReturn(notificationsListSent);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/notifications/{id}", uuid).content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBodyExpected = objectMapper.writeValueAsString(notificationsListSent);
        String responseBodyActual = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(responseBodyActual).isEqualToIgnoringWhitespace(responseBodyExpected);
    }

}
