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
public class DeleteNotificationUseCaseTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private DeleteNotificationUseCase deleteNotificationUseCase;

    @Test
    void should_callService_when_execute() {
        UUID notificationId = UUID.randomUUID();
        doNothing().when(notificationService).deleteNotification(notificationId);
        deleteNotificationUseCase.execute(notificationId);

        verify(notificationService, times(1)).deleteNotification(notificationId);
    }
}
