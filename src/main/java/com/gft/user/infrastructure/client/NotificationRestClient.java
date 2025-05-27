package com.gft.user.infrastructure.client;

import com.gft.user.application.dto.NotificationDto;
import com.gft.user.application.service.NotificationService;
import com.gft.user.infrastructure.dto.NotificationImportanceRequest;
import com.gft.user.infrastructure.dto.NotificationResponse;
import com.gft.user.infrastructure.exception.NotificationNotFoundException;
import com.gft.user.infrastructure.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class NotificationRestClient implements NotificationService {

    private final RestClient restClient;
    private final NotificationMapper notificationMapper;

    public NotificationRestClient(RestClient.Builder builder, NotificationMapper notificationMapper,  @Value("${app.communicationsBackendUrl}") String communicationsBackendUrl) {
        this.notificationMapper = notificationMapper;
        this.restClient = builder
                .baseUrl(communicationsBackendUrl)
                .build();
    }

    @Override
    public List<NotificationDto> getUserNotifications(UUID userId) {
        List<NotificationResponse> response = restClient.get()
                .uri("/notifications/{userId}", userId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (response == null) {
            return Collections.emptyList();
        }

        return response.stream().map(notificationMapper::toNotificationDto).toList();
    }

    @Override
    public void deleteNotification(UUID notificationId) {
        restClient.delete()
                .uri("/notifications/{notificationId}", notificationId)
                .retrieve()
                .onStatus(
                        status -> status == HttpStatus.NOT_FOUND,
                        (request, response) -> {
                            throw new NotificationNotFoundException(String.format("Notification with id %s not found", notificationId));
                        }
                )
                .toBodilessEntity();
    }

    @Override
    public void updateNotificationImportance(UUID notificationId, boolean importance) {
        restClient.patch()
                .uri("/notifications/{notificationId}", notificationId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new NotificationImportanceRequest(importance))
                .retrieve()
                .onStatus(
                        status -> status == HttpStatus.NOT_FOUND,
                        (request, response) -> {
                            throw new NotificationNotFoundException(String.format("Notification with id %s not found", notificationId));
                        }
                )
                .toBodilessEntity();
    }
}
