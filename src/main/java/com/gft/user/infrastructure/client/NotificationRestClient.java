package com.gft.user.infrastructure.client;

import com.gft.user.application.notification.dto.NotificationDto;
import com.gft.user.application.notification.service.NotificationService;
import com.gft.user.infrastructure.dto.NotificationResponse;
import com.gft.user.infrastructure.mapper.NotificationMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class NotificationRestClient implements NotificationService {

    private final RestClient restClient;
    private final NotificationMapper notificationMapper;

    public NotificationRestClient(RestClient.Builder builder, NotificationMapper notificationMapper) {
        this.restClient = builder
                .baseUrl("http://notificacionservice/api/v1")
                .build();
        this.notificationMapper = notificationMapper;
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

        return response.stream().map(notificationMapper::toNotificationDto).collect(Collectors.toList());
    }
}
