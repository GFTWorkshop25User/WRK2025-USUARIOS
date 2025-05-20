package com.gft.user.infrastructure.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.user.application.notification.dto.NotificationDto;
import com.gft.user.infrastructure.dto.NotificationResponse;
import com.gft.user.infrastructure.mapper.NotificationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RestClientTest(NotificationRestClient.class)
class NotificationRestClientTest {

    @Autowired
    MockRestServiceServer server;

    @Autowired
    NotificationRestClient client;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    NotificationMapper notificationMapper;

    @Test
    void should_returnNotifications_when_areNotifications() throws JsonProcessingException {
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        NotificationResponse notificationResponse1 = new NotificationResponse(UUID.randomUUID(), now, userId, "Message 1", true);
        NotificationResponse notificationResponse2 = new NotificationResponse(UUID.randomUUID(), now, userId, "Message 2", false);

        NotificationDto notificationDto1 = new NotificationDto("Message 1", now, true);
        NotificationDto notificationDto2 = new NotificationDto("Message 2", now, false);

        List<NotificationResponse> notificationResponses = List.of(notificationResponse1, notificationResponse2);
        List<NotificationDto> notificationDtos = List.of(notificationDto1, notificationDto2);

        when(notificationMapper.toNotificationDto(notificationResponse1)).thenReturn(notificationDto1);
        when(notificationMapper.toNotificationDto(notificationResponse2)).thenReturn(notificationDto2);

        server.expect(requestTo("http://notificacionservice/api/v1/notifications/" + userId))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(objectMapper.writeValueAsString(notificationResponses), MediaType.APPLICATION_JSON));

        List<NotificationDto> response = client.getUserNotifications(userId);
        assertEquals(2, response.size());
        assertTrue(response.containsAll(notificationDtos));

        server.verify();
    }

    @Test
    void should_returnEmptyList_when_responseIsNull() throws JsonProcessingException {
        UUID userId = UUID.randomUUID();

        server.expect(requestTo("http://notificacionservice/api/v1/notifications/" + userId))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(objectMapper.writeValueAsString(null), MediaType.APPLICATION_JSON));

        List<NotificationDto> response = client.getUserNotifications(userId);
        assertEquals(0, response.size());

        server.verify();
    }

    @Test
    void should_noContent_when_deleteNotification() {
        UUID notificationId = UUID.randomUUID();

        server.expect(requestTo("http://notificacionservice/api/v1/notifications/" + notificationId))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withNoContent());

        client.deleteNotification(notificationId);

        server.verify();
    }

    @Test
    void should_throwException_when_deleteNotificationNotFound() {
        UUID notificationId = UUID.randomUUID();

        server.expect(requestTo("http://notificacionservice/api/v1/notifications/" + notificationId))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withResourceNotFound());

        assertThrows(HttpClientErrorException.NotFound.class, () -> client.deleteNotification(notificationId));

        server.verify();
    }
}
