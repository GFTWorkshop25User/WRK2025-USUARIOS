package com.gft.user.application.notification.usecase;

import com.gft.user.application.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateNotificationImportanceUseCaseTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private UpdateNotificationImportanceUseCase updateNotificationImportanceUseCase;

    @Test
    void should_sendUpdatedNotification_when_Execute(){
        UUID notificationId = UUID.randomUUID();
        doNothing().when(notificationService).updateNotificationImportance(notificationId, false);
        updateNotificationImportanceUseCase.execute(notificationId, false);

        verify(notificationService, times(1)).updateNotificationImportance(notificationId, false);
    }

}
