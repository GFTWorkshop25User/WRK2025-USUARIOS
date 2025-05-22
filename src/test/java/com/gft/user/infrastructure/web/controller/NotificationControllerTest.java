package com.gft.user.infrastructure.web.controller;

import com.gft.user.application.notification.dto.NotificationDto;
import com.gft.user.application.notification.usecase.DeleteNotificationUseCase;
import com.gft.user.application.notification.usecase.GetUserNotificationsUseCase;
import com.gft.user.application.notification.usecase.UpdateNotificationImportanceUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private GetUserNotificationsUseCase getUserNotificationsUseCase;

    @Mock
    private DeleteNotificationUseCase deleteNotificationUseCase;

    @Mock
    private UpdateNotificationImportanceUseCase updateNotificationImportanceUseCase;

    @Test
    void should_returnUserNotifications_whenGetNotifications() {
        UUID uuid = UUID.randomUUID();
        List<NotificationDto> notificationsListSent = new ArrayList<>();

        NotificationDto notificationDto = new NotificationDto("Nombre actualizado", LocalDateTime.now(), true);
        notificationsListSent.add(notificationDto);

        when(getUserNotificationsUseCase.execute(uuid)).thenReturn(notificationsListSent);

        List<NotificationDto> response = notificationController.getUserNotifications(uuid);

        verify(getUserNotificationsUseCase, times(1)).execute(uuid);
        assertEquals(notificationsListSent, response);
    }

    @Test
    void should_callDeleteNotification_when_deleteNotificationCalled() {
        UUID notificationId = UUID.randomUUID();

        notificationController.deleteNotification(notificationId);

        verify(deleteNotificationUseCase).execute(notificationId);
    }

    @Test
    void should_callUpdateNotification_when_updateNotificationImportanceCalled() {
        UUID notificationId = UUID.randomUUID();
        notificationController.updateNotificationImportance(notificationId, true);
        verify(updateNotificationImportanceUseCase).execute(notificationId, true);
    }

}
