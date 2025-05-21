package com.gft.user.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.user.application.notification.dto.NotificationDto;
import com.gft.user.application.notification.usecase.DeleteNotificationUseCase;
import com.gft.user.application.notification.usecase.GetUserNotificationsUseCase;
import com.gft.user.application.notification.usecase.UpdateNotificationImportanceUseCase;
import com.gft.user.infrastructure.exception.NotificationNotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @MockitoBean
    private DeleteNotificationUseCase deleteNotificationUseCase;

    @MockitoBean
    private UpdateNotificationImportanceUseCase updateNotificationImportanceUseCase;

    @Test
    void should_returnUserNotifications_when_getUserNotificationsCalled() throws Exception {
        UUID userId = UUID.randomUUID();
        List<NotificationDto> notificationsListSent = new ArrayList<>();

        NotificationDto notificationDto = new NotificationDto("Nombre actualizado", LocalDateTime.now(), true);
        notificationsListSent.add(notificationDto);

        String requestBody = objectMapper.writeValueAsString(notificationsListSent);
        when(getUserNotificationsUseCase.execute(userId)).thenReturn(notificationsListSent);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/{id}/notifications", userId).content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBodyExpected = objectMapper.writeValueAsString(notificationsListSent);
        String responseBodyActual = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(responseBodyActual).isEqualToIgnoringWhitespace(responseBodyExpected);
    }

    @Test
    void should_returnNoContent_when_deleteNotificationCalled() throws Exception {
        UUID notificationId = UUID.randomUUID();

        doNothing().when(deleteNotificationUseCase).execute(notificationId);

        mockMvc.perform(delete("/api/v1/notifications/{id}", notificationId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(deleteNotificationUseCase, times(1)).execute(notificationId);
    }

    @Test
    void should_returnNotFound_when_deleteNotificationNotFound() throws Exception {
        UUID notificationId = UUID.randomUUID();

        doThrow(new NotificationNotFoundException("Notification not found " + notificationId)).when(deleteNotificationUseCase).execute(notificationId);

        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/notifications/{notificationId}", notificationId))
                .andExpect(status().isNotFound())
                .andReturn();

        assertEquals("Notification not found " + notificationId, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void should_updateNotificationImportance_when_called() throws Exception {
        UUID notificationId = UUID.randomUUID();

        doNothing().when(updateNotificationImportanceUseCase).execute(notificationId, true);

        mockMvc.perform(patch("/api/v1/notifications/{notificationId}", notificationId)
                .content("true").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(updateNotificationImportanceUseCase, times(1)).execute(notificationId, true);
    }

    @Test
    void should_throwException_when_notificationId_isNull_on_updateImportance() throws Exception {
        UUID notificationId = UUID.randomUUID();


        doThrow(new NotificationNotFoundException("Notification not found " + notificationId)).when(updateNotificationImportanceUseCase).execute(notificationId, false);

        MvcResult mvcResult = mockMvc.perform(patch("/api/v1/notifications/{notificationId}", notificationId)
                        .content("false").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        assertEquals("Notification not found " + notificationId, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

}
