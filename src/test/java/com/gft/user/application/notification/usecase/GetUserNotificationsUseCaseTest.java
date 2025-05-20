package com.gft.user.application.notification.usecase;

import com.gft.user.application.notification.dto.NotificationDto;
import com.gft.user.application.notification.service.NotificationService;
import com.gft.user.domain.repository.UserRepository;
import com.gft.user.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserNotificationsUseCaseTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserNotificationsUseCase getUserNotificationsUseCase;

    @Test
    void should_returnThrowException_when_userDoesntExist() {
        UUID userId = UUID.randomUUID();
        when(userRepository.existsByIdAndDisabledFalse(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> getUserNotificationsUseCase.execute(userId));
    }

    @Test
    void should_returnNotifications_when_userExists() {
        UUID userId = UUID.randomUUID();
        List<NotificationDto> notifications = List.of(new NotificationDto("Message", LocalDateTime.now(), true));

        when(userRepository.existsByIdAndDisabledFalse(userId)).thenReturn(true);
        when(notificationService.getUserNotifications(userId)).thenReturn(notifications);

        List<NotificationDto> obtainedNotifications = getUserNotificationsUseCase.execute(userId);

        assertEquals(notifications, obtainedNotifications);
    }
}
